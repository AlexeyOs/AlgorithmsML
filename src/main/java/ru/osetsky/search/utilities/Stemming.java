package ru.osetsky.search.utilities;

import org.tartarus.snowball.ext.russianStemmer;

public class Stemming {

    public static String[] setRusStemming(String[] inputArrayOfString){
        String[] result = new String[inputArrayOfString.length];
        int i = 0;
        // Пример обработки для русского языка стемминга Портрета
        russianStemmer stemmer = new russianStemmer();
        for (String str:inputArrayOfString) {
            stemmer.setCurrent(str);
            if (stemmer.stem()){
                result[i] = stemmer.getCurrent();
                i++;
            }
        }
        return result;
    }
}
