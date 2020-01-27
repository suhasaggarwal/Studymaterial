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

package bbejeck.processor.purchases;

import bbejeck.model.Purchase;
import bbejeck.model.PurchasePattern;
import bbejeck.model.RewardAccumulator;
import bbejeck.serializer.JsonDeserializer;
import bbejeck.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;

import java.util.Properties;

/**
 * User: Bill Bejeck
 * Date: 11/5/15
 * Time: 10:22 PM
 */
public class PurchaseProcessorDriver {

    public static void main(String[] args) throws Exception {

        StreamsConfig streamingConfig = new StreamsConfig(getProperties());

        JsonDeserializer<Purchase> purchaseJsonDeserializer = new JsonDeserializer<>(Purchase.class);
        JsonSerializer<Purchase> purchaseJsonSerializer = new JsonSerializer<>();
        JsonSerializer<RewardAccumulator> rewardAccumulatorJsonSerializer = new JsonSerializer<>();
        JsonSerializer<PurchasePattern> purchasePatternJsonSerializer = new JsonSerializer<>();

        StringDeserializer stringDeserializer = new StringDeserializer();
        StringSerializer stringSerializer = new StringSerializer();

        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.addSource("SOURCE", stringDeserializer, purchaseJsonDeserializer, "src-topic")

                .addProcessor("PROCESS", CreditCardAnonymizer::new, "SOURCE")
                .addProcessor("PROCESS2", PurchasePatterns::new, "PROCESS")
                .addProcessor("PROCESS3", CustomerRewards::new, "PROCESS")

                .addSink("SINK", "patterns", stringSerializer, purchasePatternJsonSerializer, "PROCESS2")
                .addSink("SINK2", "rewards",stringSerializer, rewardAccumulatorJsonSerializer, "PROCESS3")
                .addSink("SINK3", "purchases", stringSerializer, purchaseJsonSerializer, "PROCESS");

        System.out.println("Starting PurchaseProcessor Example");
        KafkaStreams streaming = new KafkaStreams(topologyBuilder, streamingConfig);
        streaming.start();
        System.out.println("Now started PurchaseProcessor Example");

    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Example-Processor-Job");
        props.put("group.id", "test-consumer-group");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "testing-processor-api");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}