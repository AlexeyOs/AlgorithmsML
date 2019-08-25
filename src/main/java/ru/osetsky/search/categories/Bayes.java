package ru.osetsky.search.categories;

import ru.osetsky.search.categories.modif.ModBayesClassifier;
import ru.osetsky.search.utilities.ReaderFile;
import ru.osetsky.search.utilities.Stemming;

import java.util.Arrays;

public class Bayes {

    public static void main(String[] args) {
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

        // Here are two unknown sentences to classify.
        String[] unknownText1 = "медицинский осмотр".split("\\s");
        String[] unknownText2 = "музыкальная композиция".split("\\s");

        unknownText1 = Stemming.setRusStemming(unknownText1);
        unknownText2 = Stemming.setRusStemming(unknownText2);

        System.out.println( // будет вывод "music"
                bayes.classify(Arrays.asList(unknownText1)).getCategory());
        System.out.println( // будет вывод "medic"
                bayes.classify(Arrays.asList(unknownText2)).getCategory());

        // Получить более подробный результат классификации.
//        System.out.println(((BayesClassifier<String, String>) bayes).classifyDetailed(
//                Arrays.asList(unknownText1)));

        // Измените объем памяти. Новые изученные классификации (используя
        // метод обучения) хранятся в очереди с заданным размером
        // здесь и используется для классификации неизвестных предложений.
        bayes.setMemoryCapacity(500);
    }

}
