/*
 * Copyright 2016 Bill Bejeck
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bbejeck.streams.twitter;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;



/**
 * User: Bill Bejeck
 * Date: 4/20/16
 * Time: 10:52 PM
 */
public class TwitterDataSource {

    private Client streamSource = null;
    private Producer<String, String> producer = null;

    public static void main(String[] args) throws Exception {
        TwitterDataSource twitterDataSource = new TwitterDataSource();
        twitterDataSource.run();
    }

    public void run()  {

        File propsFile = new File("src/main/resources/twitter-app.properties");
        if (!propsFile.exists()) {
            System.out.println("Need to have twitter-app.properties, is it still named .template");
            System.exit(1);
        }

        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propsFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        try {
            BlockingQueue<String> twitterStreamQueue = new LinkedBlockingQueue<>();
            streamSource = getTwitterClient(props, twitterStreamQueue);
            producer = getKafkaProducer();


            int maxMessages = 500000;
            int counter = 0;

            streamSource.connect();

            while (counter++ < maxMessages) {
                String twitterMessage = null;
                try {
                    twitterMessage = twitterStreamQueue.take();
                } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                }
                ProducerRecord<String, String> message = new ProducerRecord<>("twitterData", twitterMessage);
                producer.send(message);
            }

        } finally {
            stop();
        }
    }

    public void stop() {
        if (streamSource != null) {
            streamSource.stop();
            System.out.println("twitter streams stopped");
        }
        if (producer != null) {
            producer.close();
            System.out.println("kafka producer closed");
        }
    }

    private  Client getTwitterClient(Properties props, BlockingQueue<String> messageQueue) {

        String clientName = props.getProperty("clientName");
        String consumerKey = props.getProperty("consumerKey");
        String consumerSecret = props.getProperty("consumerSecret");
        String token = props.getProperty("token");
        String tokenSecret = props.getProperty("tokenSecret");
        List<String> searchTerms = Arrays.asList(props.getProperty("searchTerms").split(","));

        Authentication authentication = new OAuth1(consumerKey,consumerSecret,token,tokenSecret);
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        hosebirdEndpoint.trackTerms(searchTerms);

        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.name(clientName)
                .hosts(hosebirdHosts)
                .authentication(authentication)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(messageQueue));

          return clientBuilder.build();

    }

    private  Producer<String, String> getKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);
    }
}
