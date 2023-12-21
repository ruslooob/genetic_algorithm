package com.ruslooob.position_code.util;

import com.ruslooob.common.DoubleInterval;
import com.ruslooob.position_code.model.Individ;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ruslooob.Configuration.getConfig;

public class RandomUtils {
    private static final double PRECISION = getConfig().getPrecision();

    public static List<Individ> createRandomIndividuals(int n) {
        var generatedIndividuals = new ArrayList<Individ>(n);
        for (int i = 0; i < n; i++) {
            generatedIndividuals.add(createRandomIndivid());
        }
        return generatedIndividuals;
    }

    private static Individ createRandomIndivid() {
        DoubleInterval xInterval = getConfig().getxInterval();
        DoubleInterval yInterval = getConfig().getyInterval();
        return Individ.fromGeneticMaterial(
                generateRandomArray1(0, getNumberOfSteps(xInterval), (int) (Math.log(getNumberOfSteps(xInterval) + 1) / Math.log(2))),
                generateRandomArray1(0, getNumberOfSteps(yInterval), (int) (Math.log(getNumberOfSteps(yInterval) + 1) / Math.log(2))));
    }

    public static double generateRandomNumber(DoubleInterval interval) {
        Random random = new Random();
        return interval.start() + (interval.end() - interval.start()) * random.nextDouble();
    }

    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static char[] generateRandomArray(int length) {
        char[] randomArray = new char[length];
        Random random = new Random();

        // Generating the sign bit (0 or 1)
        int signBit = random.nextInt(2);
        randomArray[0] = (char) (signBit + '0');

        // Generating the rest of the bits
        for (int i = 1; i < length; i++) {
            int randomInt = random.nextInt(2);
            randomArray[i] = (char) (randomInt + '0');
        }

        return randomArray;
    }

    public static String generateRandomArray1(int start, int end, int totalSize) {
        int randomInt = generateRandomNumber(start, end);
        String binaryString = Integer.toBinaryString(randomInt);

        int paddingLength = Math.max(0, totalSize - binaryString.length());
        return "0".repeat(paddingLength) + binaryString;
    }

    public static int getNumberOfSteps(DoubleInterval doubleInterval) {
        return (int) Math.floor((doubleInterval.end() - doubleInterval.start()) / PRECISION) + 1;
    }
}
