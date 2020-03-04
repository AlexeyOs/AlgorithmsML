package ru.osetsky.algorithmsml.tfidf;
import ru.osetsky.algorithmsml.utilities.ReaderFile;
import ru.osetsky.algorithmsml.utilities.Stemming;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Mohamed Guendouz
 */
public class TFIDFCalculator {
    /**
     * @param doc  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    /**
     * @param docs list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(docs.size() / n);
    }

    /**
     * @param doc  a text document
     * @param docs all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(List<String> doc, List<List<String>> docs, String term) {
        return tf(doc, term) * idf(docs, term);

    }

    public static void main(String[] args) {

        List<String> doc1 = Arrays.asList("Lorem", "ipsum", "dolor", "ipsum", "sit", "ipsum");
        List<String> doc2 = Arrays.asList("Vituperata", "incorrupte", "at", "ipsum", "pro", "quo");
        List<String> doc3 = Arrays.asList("Has", "persius", "disputationi", "id", "simul");
        List<List<String>> documents = Arrays.asList(doc1, doc2, doc3);

        TFIDFCalculator calculator = new TFIDFCalculator();
        double tfidf = calculator.tfIdf(doc1, documents, "ipsum");
        double tfidf2 = calculator.tfIdf(doc2, documents, "ipsum");
        System.out.println("TF-IDF (ipsum) = " + tfidf);
        System.out.println("TF-IDF (ipsum) = " + tfidf2);


    }

    /**
     * Высчитывает вес каждого выходящего слова для каждой заданной категории,
     * после чего веса суммируются по категориям, и возвращается максимальная сумма весов для одной категории.
     * @param features
     * @return
     */
    public static double getIndexAllInputWorlds (Collection<String> features) {
        // Два примера для изучения.
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");

        music = Stemming.setRusStemming(music);
        medic = Stemming.setRusStemming(medic);

        List<String> doc1 = Arrays.asList(music);
        List<String> doc2 = Arrays.asList(medic);
        List<List<String>> documents = Arrays.asList(doc1, doc2);

        TFIDFCalculator calculator = new TFIDFCalculator();
        double sumtridf1 = 0;
        //doc1
        for (String feature:features) {
            double tfidf1 = calculator.tfIdf(doc1, documents, feature);
            if (!Double.isNaN(tfidf1)) {
                sumtridf1 = sumtridf1 + tfidf1;
            }
        }
//        System.out.println("TF-IDF (doc1) = " + sumtridf1);
        //doc2
        double sumtridf2 = 0;
        for (String feature:features) {
            double tfidf2 = calculator.tfIdf(doc2, documents, feature);
            if (!Double.isNaN(tfidf2)) {
                sumtridf2 = sumtridf2 + tfidf2;
            }
        }

//        System.out.println("TF-IDF (doc2) = " + sumtridf2);
        if (sumtridf1 > sumtridf2) {
            return sumtridf1;
        } else {
            return sumtridf2;
        }
    }


}
