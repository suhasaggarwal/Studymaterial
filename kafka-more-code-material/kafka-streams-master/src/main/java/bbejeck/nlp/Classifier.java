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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Bill Bejeck
 * Date: 4/20/16
 * Time: 10:01 PM
 */
public class Classifier {

    private DynamicLMClassifier<NGramBoundaryLM> classifier;
    private int maxCharNGram = 3;
    private String trainingDataDelimiter;

    public Classifier(String trainingDataDelimiter) {
        this.trainingDataDelimiter = trainingDataDelimiter;
    }

    public Classifier(){
         this("#");
    }

    public void train(File trainingData)  {
        Set<String> categorySet = new HashSet<>();
        List<String[]> annotatedData =  new ArrayList<>();
        fillCategoriesAndAnnotatedData(trainingData, categorySet, annotatedData);
        trainClassifier(categorySet, annotatedData);
    }

    private void trainClassifier(Set<String> categorySet, List<String[]> annotatedData){
        String[] categories = categorySet.toArray(new String[0]);
         classifier = DynamicLMClassifier.createNGramBoundary(categories,maxCharNGram);
        for (String[] row: annotatedData) {
            String actualClassification = row[0];
            String text = row[1];
            Classification classification = new Classification(actualClassification);
            Classified<CharSequence> classified = new Classified<>(text,classification);
            classifier.handle(classified);
        }
    }


    private void fillCategoriesAndAnnotatedData(File trainingData,
                                                Set<String> categorySet,
                                                List<String[]> annotatedData) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(trainingData)))) {

            String line = reader.readLine();
            while (line != null) {
                String[] data = line.split(trainingDataDelimiter);
                categorySet.add(data[0]);
                annotatedData.add(data);
                line = reader.readLine();
            }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public String classify(String text){
        return  classifier.classify(text.trim()).bestCategory().toLowerCase();
    }

}
