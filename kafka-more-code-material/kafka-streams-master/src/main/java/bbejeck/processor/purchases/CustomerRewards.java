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
import bbejeck.model.RewardAccumulator;
import org.apache.kafka.streams.processor.AbstractProcessor;

/**
 * User: Bill Bejeck
 * Date: 2/20/16
 * Time: 9:44 AM
 */
public class CustomerRewards extends AbstractProcessor<String,Purchase> {

    @Override
    public void process(String key, Purchase value) {
        RewardAccumulator accumulator = RewardAccumulator.builder(value).build();
        context().forward(key,accumulator);
        context().commit();

    }
}
