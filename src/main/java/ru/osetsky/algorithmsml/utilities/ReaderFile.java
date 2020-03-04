package ru.osetsky.algorithmsml.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderFile {

    public static String[] readArraysOfStringFromFile(String filename) {
        //определяем пустой массив изначально
        String[] text = new String[0];
        try {
            String str = null;
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((str = br.readLine()) != null) {
                //получаем новые слова
                String[] newWords = str.split(" ");
                //создаем расширенный массив
                String[] result = new String[text.length + newWords.length];
                //копируем элементы в массив
                System.arraycopy(text, 0, result, 0, text.length);
                System.arraycopy(newWords, 0, result, text.length, newWords.length);
                //присваиваем результирующий массив текущему
                text = result;
            }
            br.close();
        } catch (IOException exc) {
            System.out.println("IO error!" + exc);
        }
        return text;
    }
}
