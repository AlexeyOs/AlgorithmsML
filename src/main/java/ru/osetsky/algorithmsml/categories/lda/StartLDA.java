package ru.osetsky.algorithmsml.categories.lda;

import ru.osetsky.algorithmsml.utilities.ReaderFile;
import ru.osetsky.algorithmsml.utilities.Stemming;

import java.util.Arrays;
import java.util.List;

public class StartLDA {
    public static void main(String[] args) {
        LogisticDiscriminantAnalysis lda = new LogisticDiscriminantAnalysis();

        // Два примера для изучения.
        String[] music = ReaderFile.readArraysOfStringFromFile("music.txt");
        String[] medic = ReaderFile.readArraysOfStringFromFile("medic.txt");

        music = Stemming.setRusStemming(music);
        medic = Stemming.setRusStemming(medic);

        List<String> musicDocs = Arrays.asList(music);
        List<String> medicDocs = Arrays.asList(medic);
        lda.setLogisticDiscriminantAnalysis(musicDocs);
        lda.setLogisticDiscriminantAnalysis(medicDocs);

    }
}
