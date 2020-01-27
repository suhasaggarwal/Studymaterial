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

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * User: Bill Bejeck
 * Date: 3/28/16
 * Time: 8:55 PM
 */
public class HoseBirdTester {

    public static void main(String[] args)  throws InterruptedException {

        BlockingQueue<String> msgQueue = new LinkedBlockingDeque<>();



        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        List<String> terms = Lists.newArrayList("superman vs batman","#supermanvsbatman");
        hosebirdEndpoint.trackTerms(terms);




        Authentication hosebirdAuth  = new OAuth1("18qydWMuiUohwCtQpp1MOFCFr",
                                                  "YrYhYd09LKZLbhsKT1o4XcEPl6HiAoNykiOxYBq0dAB8t0vRCo",
                                                  "16972669-KSvyDEMc7dussPfW6a9Ru65L4eWGj637ciHLHZLyn",
                                                  "ky53NE6cbBvtNLopto7o9gVyHDejSB2kPsRhHGKEd1MrS");


        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.name("bbejeck-hosebird")
                     .hosts(hosebirdHosts)
                     .authentication(hosebirdAuth)
                     .endpoint(hosebirdEndpoint)
                     .processor(new StringDelimitedProcessor(msgQueue));

        Client hosebirdClient = clientBuilder.build();
        hosebirdClient.connect();

        for (int msgRead = 0; msgRead < 100; msgRead++) {
            String msg = msgQueue.take();
            System.out.println(msg);
        }

        hosebirdClient.stop();

    }
}
