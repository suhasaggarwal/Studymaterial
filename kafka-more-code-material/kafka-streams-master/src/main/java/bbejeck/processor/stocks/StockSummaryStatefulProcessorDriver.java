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

package bbejeck.processor.stocks;

import bbejeck.model.StockTransaction;
import bbejeck.model.StockTransactionSummary;
import bbejeck.serializer.JsonDeserializer;
import bbejeck.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.apache.kafka.streams.state.Stores;

import java.util.Properties;

/**
 * User: Bill Bejeck
 * Date: 2/8/16
 * Time: 5:11 PM
 */
public class StockSummaryStatefulProcessorDriver {

    public static void main(String[] args) {

        StreamsConfig streamingConfig = new StreamsConfig(getProperties());

        TopologyBuilder builder = new TopologyBuilder();

        JsonSerializer<StockTransactionSummary> stockTxnSummarySerializer = new JsonSerializer<>();
        JsonDeserializer<StockTransactionSummary> stockTxnSummaryDeserializer = new JsonDeserializer<>(StockTransactionSummary.class);
        JsonDeserializer<StockTransaction> stockTxnDeserializer = new JsonDeserializer<>(StockTransaction.class);
        JsonSerializer<StockTransaction> stockTxnJsonSerializer = new JsonSerializer<>();
        StringSerializer stringSerializer = new StringSerializer();
        StringDeserializer stringDeserializer = new StringDeserializer();

        Serde<StockTransactionSummary> stockTransactionSummarySerde = Serdes.serdeFrom(stockTxnSummarySerializer,stockTxnSummaryDeserializer);

        builder.addSource("stocks-source", stringDeserializer, stockTxnDeserializer, "stocks")
                       .addProcessor("summary", StockSummaryProcessor::new, "stocks-source")
                       .addStateStore(Stores.create("stock-transactions").withStringKeys()
                               .withValues(stockTransactionSummarySerde).inMemory().maxEntries(100).build(),"summary")
                       .addSink("sink", "stocks-out", stringSerializer,stockTxnJsonSerializer,"stocks-source")
                       .addSink("sink-2", "transaction-summary", stringSerializer, stockTxnSummarySerializer, "summary");

        System.out.println("Starting StockSummaryStatefulProcessor Example");
        KafkaStreams streaming = new KafkaStreams(builder, streamingConfig);
        streaming.start();
        System.out.println("StockSummaryStatefulProcessor Example now started");

    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, "Sample-Stateful-Processor");
        props.put("group.id", "test-consumer-group");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "stateful_processor_id");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, 1);
        props.put(StreamsConfig.TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class);
        return props;
    }
}
