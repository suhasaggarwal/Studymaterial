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
import org.apache.kafka.streams.processor.AbstractProcessor;

/**
 * User: Bill Bejeck
 * Date: 2/20/16
 * Time: 9:19 AM
 */
public class CreditCardAnonymizer extends AbstractProcessor<String, Purchase> {

    private static final String CC_NUMBER_REPLACEMENT="xxxx-xxxx-xxxx-";

    @Override
    public void process(String key, Purchase purchase) {
          String last4Digits = purchase.getCreditCardNumber().split("-")[3];
          Purchase updated = Purchase.builder(purchase).creditCardNumber(CC_NUMBER_REPLACEMENT+last4Digits).build();
          context().forward(key,updated);
          context().commit();
    }
}
