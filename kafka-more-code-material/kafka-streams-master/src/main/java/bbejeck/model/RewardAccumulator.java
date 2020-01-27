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

package bbejeck.model;

/**
 * User: Bill Bejeck
 * Date: 2/20/16
 * Time: 9:55 AM
 */
public class RewardAccumulator {

    private String customerName;
    private double purchaseTotal;

    private RewardAccumulator(String customerName, double purchaseTotal) {
        this.customerName = customerName;
        this.purchaseTotal = purchaseTotal;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getPurchaseTotal() {
        return purchaseTotal;
    }

    @Override
    public String toString() {
        return "RewardAccumulator{" +
                "customerName='" + customerName + '\'' +
                ", purchaseTotal=" + purchaseTotal +
                '}';
    }

    public static Builder builder(Purchase purchase){return new Builder(purchase);}

    public static final class Builder {
        private String customerName;
        private double purchaseTotal;

        private Builder(Purchase purchase){
           this.customerName = purchase.getLastName()+","+purchase.getFirstName();
           this.purchaseTotal = purchase.getPrice() * purchase.getQuantity();
        }


        public RewardAccumulator build(){
            return new RewardAccumulator(customerName,purchaseTotal);
        }

    }
}
