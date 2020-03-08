package ru.osetsky.algorithmsml.categories;

import de.daslaboratorium.machinelearning.classifier.Classification;
import de.daslaboratorium.machinelearning.classifier.Classifier;
import de.daslaboratorium.machinelearning.classifier.bayes.BayesClassifier;
import org.junit.Test;
import ru.osetsky.algorithmsml.categories.bayes.ModBayesClassifier;
import ru.osetsky.algorithmsml.utilities.ReaderFile;
import ru.osetsky.algorithmsml.utilities.Stemming;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

public class StartBayesTest {

    @Test
    public void modifiedBayesmethodWithTfIdf() {
        // Создан новый классификатор Байеса со строковыми категориями и строковыми элементами.
        ModBayesClassifier<String, String> bayes = new ModBayesClassifier<String, String>();

        // Два примера для изучения.
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");

        music = Stemming.setRusStemming(music);
        medic = Stemming.setRusStemming(medic);

        // Учитесь, классифицируя примеры.
        // Новые категории могут быть добавлены на лету, когда они впервые используются.
        // Классификация состоит из категории и списка признаков
        // что привело к классификации в этой категории.
        bayes.learn("music", Arrays.asList(music));
        bayes.learn("medic", Arrays.asList(medic));

        // Вот два неизвестных предложения для классификации.
        String[] unknownText1 = "медицинский осмотр".split("\\s");
        System.out.println("Первый контрольный пример: ");
        for (String word:unknownText1) {

            System.out.print(word);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("Второй контрольный пример: ");
        String[] unknownText2 = "музыкальная композиция".split("\\s");
        for (String word:unknownText2) {
            System.out.print(word);
            System.out.print(" ");
        }
        System.out.println();
        unknownText1 = Stemming.setRusStemming(unknownText1);
        unknownText2 = Stemming.setRusStemming(unknownText2);

        System.out.println("Принадлежность первого контрольного примера к категории: ");
        System.out.println(bayes.classify(Arrays.asList(unknownText1)).getCategory());
        assertEquals("medic",bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println("Принадлежность второго контрольного примера к категории: ");
        System.out.println(bayes.classify(Arrays.asList(unknownText2)).getCategory());
        assertEquals("music",bayes.classify(Arrays.asList(unknownText2)).getCategory());

        System.out.println("Первый дополнительно: ");
        // Получить более подробный результат классификации.
        Collection<Classification<String, String>> result = ((ModBayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));
        for (Iterator<Classification<String, String>> it = result.iterator(); it.hasNext(); ) {
            Classification<String, String> f = it.next();
            System.out.println(f);
        }
        System.out.println("Второй дополнительно: ");
        Collection<Classification<String, String>> result2 = ((ModBayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText2));
        for (Iterator<Classification<String, String>> it = result2.iterator(); it.hasNext(); ) {
            Classification<String, String> f = it.next();
            System.out.println(f);
        }

        // Измените объем памяти. Новые изученные классификации (используя
        // метод обучения) хранятся в очереди с заданным размером
        // здесь и используется для классификации неизвестных предложений.
        bayes.setMemoryCapacity(500);
    }

    @Test
    public void sourceBayesmethodWithTfIdf() {
        // Создан новый классификатор Байеса со строковыми категориями и строковыми элементами.
        Classifier<String, String> bayes = new BayesClassifier<String, String>();

        // Два примера для изучения.
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");

        music = Stemming.setRusStemming(music);
        medic = Stemming.setRusStemming(medic);

        // Учитесь, классифицируя примеры.
        // Новые категории могут быть добавлены на лету, когда они впервые используются.
        // Классификация состоит из категории и списка признаков
        // что привело к классификации в этой категории.
        bayes.learn("music", Arrays.asList(music));
        bayes.learn("medic", Arrays.asList(medic));

        // Вот два неизвестных предложения для классификации.
        String[] unknownText1 = "медицинский осмотр".split("\\s");
        System.out.println("Первый контрольный пример: ");
        for (String word:unknownText1) {

            System.out.print(word);
            System.out.print(" ");
        }
        System.out.println();
        System.out.println("Второй контрольный пример: ");
        String[] unknownText2 = "музыкальная композиция".split("\\s");
        for (String word:unknownText2) {
            System.out.print(word);
            System.out.print(" ");
        }
        System.out.println();
        unknownText1 = Stemming.setRusStemming(unknownText1);
        unknownText2 = Stemming.setRusStemming(unknownText2);

        System.out.println("Принадлежность первого контрольного примера к категории: ");
        System.out.println(bayes.classify(Arrays.asList(unknownText1)).getCategory());
        assertEquals("medic",bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println("Принадлежность второго контрольного примера к категории: ");
        System.out.println(bayes.classify(Arrays.asList(unknownText2)).getCategory());
        assertEquals("music",bayes.classify(Arrays.asList(unknownText2)).getCategory());

        System.out.println("Первый дополнительно: ");
        // Получить более подробный результат классификации.
        Collection<Classification<String, String>> result = ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText1));
        for (Iterator<Classification<String, String>> it = result.iterator(); it.hasNext(); ) {
            Classification<String, String> f = it.next();
            System.out.println(f);
        }
        System.out.println("Второй дополнительно: ");
        Collection<Classification<String, String>> result2 = ((BayesClassifier<String, String>) bayes).classifyDetailed(
                Arrays.asList(unknownText2));
        for (Iterator<Classification<String, String>> it = result2.iterator(); it.hasNext(); ) {
            Classification<String, String> f = it.next();
            System.out.println(f);
        }

        // Измените объем памяти. Новые изученные классификации (используя
        // метод обучения) хранятся в очереди с заданным размером
        // здесь и используется для классификации неизвестных предложений.
        bayes.setMemoryCapacity(500);
    }

}