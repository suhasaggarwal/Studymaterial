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

import bbejeck.model.Tweet;
import bbejeck.nlp.Classifier;
import bbejeck.serializer.JsonDeserializer;
import bbejeck.serializer.JsonSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * User: Bill Bejeck
 * Date: 4/21/16
 * Time: 8:51 PM
 */
@SuppressWarnings("unchecked")
public class TwitterStreamsAnalyzer {

    private KafkaStreams kafkaStreams;

    public static void main(String[] args) throws  IOException {
        TwitterStreamsAnalyzer streamsAnalyzer = new TwitterStreamsAnalyzer();
        streamsAnalyzer.run();
    }

    public void run()  {
        StreamsConfig streamsConfig = new StreamsConfig(getProperties());

        JsonSerializer<Tweet> tweetJsonSerializer = new JsonSerializer<>();
        JsonDeserializer<Tweet> tweetJsonDeserializer = new JsonDeserializer<>(Tweet.class);
        Serde<Tweet> tweetSerde = Serdes.serdeFrom(tweetJsonSerializer, tweetJsonDeserializer);

        KStreamBuilder kStreamBuilder = new KStreamBuilder();

        Classifier classifier = new Classifier();
        classifier.train(new File("src/main/resources/kafkaStreamsTwitterTrainingData_clean.csv"));

        KeyValueMapper<String, Tweet, String> languageToKey = (k, v) ->
           StringUtils.isNotBlank(v.getText()) ? classifier.classify(v.getText()):"unknown";

        Predicate<String, Tweet> isEnglish = (k, v) -> k.equals("english");
        Predicate<String, Tweet> isFrench =  (k, v) -> k.equals("french");
        Predicate<String, Tweet> isSpanish = (k, v) -> k.equals("spanish");

        KStream<String, Tweet> tweetKStream = kStreamBuilder.stream(Serdes.String(), tweetSerde, "twitterData");

        KStream<String, Tweet>[] filteredStreams = tweetKStream.selectKey(languageToKey).branch(isEnglish, isFrench, isSpanish);

        filteredStreams[0].to(Serdes.String(), tweetSerde, "english");
        filteredStreams[1].to(Serdes.String(), tweetSerde, "french");
        filteredStreams[2].to(Serdes.String(), tweetSerde, "spanish");

        kafkaStreams = new KafkaStreams(kStreamBuilder, streamsConfig);
        System.out.println("Starting twitter analysis streams");
        kafkaStreams.start();
        System.out.println("Started");

    }

    public void stop() {
        kafkaStreams.close();
        System.out.println("KStreams stopped");
    }


    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Twitter-Streams-Analysis");
        props.put("group.id", "twitter-streams");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "twitter-streams-id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}
