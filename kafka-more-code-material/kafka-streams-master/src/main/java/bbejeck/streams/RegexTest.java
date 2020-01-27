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

package bbejeck.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Example on Using Regex Patterns For Source Topics
 */
public class RegexTest {


    public static void main(String[] args) {

        StreamsConfig streamingConfig = new StreamsConfig(getProperties());
        KStreamBuilder kStreamBuilder = new KStreamBuilder();


        KStream<String, String> patternStreamI = kStreamBuilder.stream(Serdes.String(), Serdes.String(), Pattern.compile("topic-\\d"));
        KStream<String, String> namedTopicKStream = kStreamBuilder.stream(Serdes.String(), Serdes.String(), "topic-Z");
        KStream<String, String> patternStreamII = kStreamBuilder.stream(Serdes.String(), Serdes.String(), Pattern.compile("topic-[A-Y]+"));

        patternStreamI.print("pattern-\\d");
        namedTopicKStream.print("topic-Z");
        patternStreamII.print("topic-[A-Y]+");


        System.out.println("Starting stream regex consumer Example");
        KafkaStreams kafkaStreams = new KafkaStreams(kStreamBuilder, streamingConfig);
        kafkaStreams.start();


    }


    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "regex_id");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "regex_group_id");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "regex_id");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, "2");
        props.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "15000");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}
