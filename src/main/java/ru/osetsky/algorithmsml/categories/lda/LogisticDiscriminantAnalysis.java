package ru.osetsky.algorithmsml.categories.lda;

import com.github.chen0040.data.utils.TupleTwo;
import com.github.chen0040.lda.Doc;
import com.github.chen0040.lda.Lda;
import com.github.chen0040.lda.LdaResult;

import java.util.Arrays;
import java.util.List;

/**
 * Сслыка на библиотеку
 * https://github.com/chen0040/java-lda
 */
public class LogisticDiscriminantAnalysis {

    public void setLogisticDiscriminantAnalysis(List<String> docs) {
        int topicCount = 2;


        Lda method = new Lda();
        method.setTopicCount(topicCount);
        method.setMaxVocabularySize(20000);

        LdaResult result = method.fit(docs);

        System.out.println("Topic Count: " + result.topicCount());

        //TODO разобраться как задается матрица
        for(int topicIndex = 0; topicIndex < topicCount; ++topicIndex){
            String topicSummary = result.topicSummary(topicIndex);
            List<TupleTwo<String, Integer>> topKeyWords = result.topKeyWords(topicIndex, 10);
            List<TupleTwo<Doc, Double>> topStrings = result.topDocuments(topicIndex, 5);

            System.out.println("Topic #" + (topicIndex+1) + ": " + topicSummary);

            for(TupleTwo<String, Integer> entry : topKeyWords){
                String keyword = entry._1();
                int score = entry._2();
                System.out.println("Keyword: " + keyword + "(" + score + ")");
            }

            for(TupleTwo<Doc, Double> entry : topStrings){
                double score = entry._2();
                int docIndex = entry._1().getDocIndex();
                String docContent = entry._1().getContent();
                System.out.println("Doc (" + docIndex + ", " + score + ")): " + docContent);
            }
        }
    }
}
