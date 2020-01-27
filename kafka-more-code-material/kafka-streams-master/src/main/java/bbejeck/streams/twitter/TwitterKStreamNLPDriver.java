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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: Bill Bejeck
 * Date: 4/27/16
 * Time: 8:46 PM
 */
public class TwitterKStreamNLPDriver {

    public static void main(String[] args) {

        System.out.println("Starting the KStreams Twitter analysis");

        ExecutorService service = Executors.newFixedThreadPool(2);
        TwitterDataSource twitterDataSource = new TwitterDataSource();
        TwitterStreamsAnalyzer twitterStreamsAnalyzer = new TwitterStreamsAnalyzer();

        Runnable dataSourceRunner = twitterDataSource::run;
        Runnable streamsRunner = twitterStreamsAnalyzer::run;

        service.submit(dataSourceRunner);
        service.submit(streamsRunner);

        try {
            //run for 10 minutes
            Thread.sleep(600000);
        } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
        } finally {
            System.out.println("shutting down now");
            service.shutdownNow();
            twitterDataSource.stop();
            twitterStreamsAnalyzer.stop();
            System.out.println("all done....");
        }

    }
}
