package com.ruslooob.real_number.util;

import com.ruslooob.Configuration;
import com.ruslooob.common.DoubleInterval;
import com.ruslooob.real_number.model.Individ;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ruslooob.Configuration.getConfig;

public class RandomUtils {
    public static List<Individ> createRandomIndividuals(int n) {
        var generatedIndividuals = new ArrayList<Individ>(n);
        for (int i = 0; i < n; i++) {
            generatedIndividuals.add(createRandomIndivid());
        }
        return generatedIndividuals;
    }

    private static Individ createRandomIndivid() {
        return Individ.fromGeneticMaterial(
                new double[]{generateRandomNumber(getConfig().getxInterval()), generateRandomNumber(getConfig().getyInterval())});
    }

    public static double generateRandomNumber(DoubleInterval interval) {
        Random random = new SecureRandom();
        return interval.start() + (interval.end() - interval.start()) * random.nextDouble();
    }
}
