package ru.osetsky.algorithmsml.utilities;

public class WordsProcessing {

    public static String[] filterLatinWords(String[] arraysString) {
        String[] filterArraysString = new String[arraysString.length];
        int i = 0;
        for (String string:arraysString) {
            if (!string.contains(";")
                    && string.equals(string.toLowerCase())
                    && !string.contains(",")
                    && !string.contains("\"")
                    && !string.contains("=")
                    && !string.contains("+")
                    && !string.contains("!")
                    && !string.contains(")")
                    && !string.contains("(")
                    && !string.contains(".")
                    && !string.contains("|")
                    && !string.contains("'")
                    && !string.contains("-")
                    && !string.contains("*")
                    && !string.contains("[")
                    && !string.contains("]")
                    && !string.contains("{")
                    && !string.contains("}")
                    && !string.contains("/")
                    && !string.contains("&")
                    && !string.contains(":")
                    && !string.contains("#")
                    && !string.contains("?")
                    && !string.contains("~")
                    && !string.contains("’")
                    && !string.contains("‘")
                    && !string.contains("»")
                    && !string.contains("«")
                    && !string.contains(">")
                    && !string.contains("<")
                    && !string.contains("№")
                    && !string.contains("\\")
                    && !string.contains("%")
                    && !string.contains("_")
                    && !string.contains("\u20BD")
                    && !string.contains("0")
                    && !string.contains("1")
                    && !string.contains("2")
                    && !string.contains("3")
                    && !string.contains("4")
                    && !string.contains("5")
                    && !string.contains("6")
                    && !string.contains("7")
                    && !string.contains("8")
                    && !string.contains("9")
                    && !string.contains("—")
                    && !string.contains("©")
                    && !string.matches("^[a-zA-Z0-9]+$")){
                filterArraysString[i] = string;
                i++;
            }
        }
        String[] result = new String[i];
        int j = 0;
        for (String str:filterArraysString) {
            if (str != null){
                result[j] = str;
                j++;
            }
        }
        return result;
    }

    public static String[] wordsSeparation(String str) {
        String[] subStr;
        String delimeter = " "; // Разделитель
        subStr = str.split(delimeter); // Разделения строки str с помощью метода split()
        // Вывод результата на экран
        for(int i = 0; i < subStr.length; i++) {
//            System.out.println(subStr[i]);
        }
        return subStr;
    }
}
