package ru.osetsky.algorithmsml.categories.modif;

import de.daslaboratorium.machinelearning.classifier.Classification;
import de.daslaboratorium.machinelearning.classifier.Classifier;

import ru.osetsky.algorithmsml.tfidf.TFIDFCalculator;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ModBayesClassifier<T, K> extends Classifier<T, K> {
    public ModBayesClassifier() {
    }

    private float featuresProbabilityProduct(Collection<T> features, K category) {
        float product = 1.0F;

        Object feature;
        Collection<String> featuresStr = (Collection<String>) features;
        int sumTfidfWeigft = (int)TFIDFCalculator.getIndexAllInputWorlds(featuresStr);
        if (sumTfidfWeigft > 0) {
            for(Iterator var4 = features.iterator(); var4.hasNext(); product *= this.featureWeighedAverage((T) feature, category,null,sumTfidfWeigft,0.5F)) {
                feature = var4.next();
            }
        } else {
            for(Iterator var4 = features.iterator(); var4.hasNext(); product *= this.featureWeighedAverage((T) feature, category,null,0.01F,0.5F)) {
                feature = var4.next();
            }
        }

        return product;
    }

    private float categoryProbability(Collection<T> features, K category) {
        return (float)this.getCategoryCount(category) / (float)this.getCategoriesTotal() * this.featuresProbabilityProduct(features, category);
    }

    private SortedSet<Classification<T, K>> categoryProbabilities(Collection<T> features) {
        SortedSet<Classification<T, K>> probabilities = new TreeSet(new Comparator<Classification<T, K>>() {
            public int compare(Classification<T, K> o1, Classification<T, K> o2) {
                int toReturn = Float.compare(o1.getProbability(), o2.getProbability());
                if (toReturn == 0 && !o1.getCategory().equals(o2.getCategory())) {
                    toReturn = -1;
                }

                return toReturn;
            }
        });
        Iterator var3 = this.getCategories().iterator();

        while(var3.hasNext()) {
            K category = (K) var3.next();
            probabilities.add(new Classification(features, category, this.categoryProbability(features, category)));
        }

        return probabilities;
    }

    public Classification<T, K> classify(Collection<T> features) {
        SortedSet<Classification<T, K>> probabilites = this.categoryProbabilities(features);
        return probabilites.size() > 0 ? (Classification)probabilites.last() : null;
    }

    public Collection<Classification<T, K>> classifyDetailed(Collection<T> features) {
        return this.categoryProbabilities(features);
    }
}
