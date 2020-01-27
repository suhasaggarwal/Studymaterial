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
import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Objects;

/**
 * User: Bill Bejeck
 * Date: 1/25/16
 * Time: 8:13 PM
 */

@SuppressWarnings("unchecked")
public class StockSummaryProcessor extends AbstractProcessor<String, StockTransaction> {

    private KeyValueStore<String, StockTransactionSummary> summaryStore;
    private ProcessorContext context;


    public void process(String key, StockTransaction stockTransaction) {
        String currentSymbol = stockTransaction.getSymbol();
        StockTransactionSummary transactionSummary = summaryStore.get(currentSymbol);
        if (transactionSummary == null) {
            transactionSummary = StockTransactionSummary.fromTransaction(stockTransaction);
        } else {
            transactionSummary.update(stockTransaction);
        }
        summaryStore.put(currentSymbol, transactionSummary);

        this.context.commit();
    }


    @Override
    @SuppressWarnings("unchecked")
    public void init(ProcessorContext context) {
        this.context = context;
        this.context.schedule(10000);
        summaryStore = (KeyValueStore<String, StockTransactionSummary>) this.context.getStateStore("stock-transactions");
        Objects.requireNonNull(summaryStore, "State store can't be null");

    }


    @Override
    public void punctuate(long streamTime) {
        KeyValueIterator<String, StockTransactionSummary> it = summaryStore.all();
        long currentTime = System.currentTimeMillis();
        while (it.hasNext()) {
            StockTransactionSummary summary = it.next().value;
            if (summary.updatedWithinLastMillis(currentTime, 11000)) {
                this.context.forward(summary.tickerSymbol, summary);
            }
        }
    }

}
