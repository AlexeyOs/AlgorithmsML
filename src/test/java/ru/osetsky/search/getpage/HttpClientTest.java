package ru.osetsky.search.getpage;

import de.daslaboratorium.machinelearning.classifier.Classification;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import org.junit.Before;
import org.junit.Test;
import ru.osetsky.search.utilities.ReaderFile;
import ru.osetsky.search.utilities.Stemming;
import ru.osetsky.search.utilities.WordsProcessing;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;


import static org.junit.Assert.assertEquals;

public class HttpClientTest {

    private String textResponce;
    private Classifier<String, String> bayes = new BayesClassifier<String, String>();

    @Before
    public void setStudy() {
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");
        music = Stemming.setRusStemming(music);
        medic = Stemming.setRusStemming(medic);
        bayes.learn("medic", Arrays.asList(medic));
        bayes.learn("music", Arrays.asList(music));
        bayes.setMemoryCapacity(100);
    }

    @Test
    public void getHttpClientForMedicGroup() {
        String[] sitesMedic = {
                "https://www.kapmed.ru/",
                "https://vtbms.ru/",
                "https://www.medicina.ru/o-klinike/o-sayte/"
        };
        System.out.println("МЕДИЦИНА");
        for (String siteMedic : sitesMedic) {
            System.out.println();
            System.out.println(siteMedic);
            System.out.println();
            textResponce = HttpsClient.getHttpsClient(siteMedic);
            String[] filteredWords = WordsProcessing.filterLatinWords(WordsProcessing.wordsSeparation(textResponce));
            filteredWords = Stemming.setRusStemming(filteredWords);
            System.out.println("Категория сайта: " + siteMedic);
            System.out.println(filteredWords.length);
            System.out.println(bayes.classify(Arrays.asList(filteredWords)).getCategory());
            Collection<Classification<String, String>> result = ((BayesClassifier<String, String>) bayes).classifyDetailed(
                    Arrays.asList(filteredWords));
            for (Iterator<Classification<String, String>> it = result.iterator(); it.hasNext(); ) {
                Classification<String, String> f = it.next();
                System.out.println(f);
            }
        }

    }

    @Test
    public void getHttpClientForMusicGroup() {
        String[] sitesMusic = {
                "https://www.muzexp.com/",
                "https://arsenal-music.ru/",
                "https://mirm.ru/"
        };
        System.out.println("МУЗЫКА");
        for (String siteMusic : sitesMusic) {
            System.out.println();
            System.out.println(siteMusic);
            System.out.println();
            textResponce = HttpsClient.getHttpsClient(siteMusic);
            String[] filteredWords = WordsProcessing.filterLatinWords(WordsProcessing.wordsSeparation(textResponce));
            filteredWords = Stemming.setRusStemming(filteredWords);
            System.out.println("Категория сайта: " + siteMusic);
            // Получить более подробный результат классификации.
            Collection<Classification<String, String>> result = ((BayesClassifier<String, String>) bayes).classifyDetailed(
                    Arrays.asList(filteredWords));
            for (Iterator<Classification<String, String>> it = result.iterator(); it.hasNext(); ) {
                Classification<String, String> f = it.next();
                System.out.println(f);
            }
        }

    }




}