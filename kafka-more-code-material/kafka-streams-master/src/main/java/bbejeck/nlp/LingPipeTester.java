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

package bbejeck.nlp;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.lm.NGramBoundaryLM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Bill Bejeck
 * Date: 4/9/16
 * Time: 6:24 PM
 */
public class LingPipeTester {

    public static void main(String[] args) throws  Exception {
        File trainingData = new File("src/main/resources/kafkaStreamsTwitterTrainingData_clean.csv");
        int maxCharNGram = 3;

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainingData)));
        Set<String> categorySet = new HashSet<>();
        List<String[]> annotatedData =  new ArrayList<>();
        String line = reader.readLine();
        while (line !=null){
              String[] data = line.split("#");
              categorySet.add(data[0]);
              annotatedData.add(data);
              line = reader.readLine();
        }
        System.out.println("read in all data");
        reader.close();
        String[] categories = categorySet.toArray(new String[0]);

        DynamicLMClassifier<NGramBoundaryLM> classifier
                = DynamicLMClassifier.createNGramBoundary(categories,maxCharNGram);
        for (String[] row: annotatedData) {
            String truth = row[0];
            String text = row[1];
            Classification classification = new Classification(truth);
            Classified<CharSequence> classified = new Classified<>(text,classification);
            classifier.handle(classified);
        }
        System.out.println("training complete");

        reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("enter text, I'll tell you the language");
        String text;
        while (!(text = reader.readLine()).equalsIgnoreCase("quit")) {
            Classification classification = classifier.classify(text);
            System.out.println("Entered -> " + text);
            System.out.println("lang -> " + classification.bestCategory());
        }
    }
}
