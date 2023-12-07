package com.ruslooob.position_code.model;

import com.ruslooob.position_code.util.DiscreteIntervalConverter;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static com.ruslooob.Configuration.getConfig;
import static com.ruslooob.common.Functions.bransinsFunction;

public class Individ implements Comparable<Individ> {
    private final Random random = new SecureRandom();
    private static final DiscreteIntervalConverter xIntervalConverter = new DiscreteIntervalConverter(
            getConfig().getxInterval(), getConfig().getPrecision());
    private static final DiscreteIntervalConverter yIntervalConverter = new DiscreteIntervalConverter(
            getConfig().getyInterval(), getConfig().getPrecision());

    private final StringBuilder geneticMaterialX = new StringBuilder();
    private final StringBuilder geneticMaterialY = new StringBuilder();
    private Double fitness;

    private Individ(String geneticMaterialX, String geneticMaterialY) {
        if (Integer.parseInt(geneticMaterialX, 2) > xIntervalConverter.getNumberOfSteps()) {
            throw new IllegalArgumentException("invalida genetic material X");
        }

        if (Integer.parseInt(geneticMaterialY, 2) > yIntervalConverter.getNumberOfSteps()) {
            throw new IllegalArgumentException("invalida genetic material Y");
        }

        this.geneticMaterialX.append(geneticMaterialX);
        this.geneticMaterialY.append(geneticMaterialY);
    }

    public static Individ fromGeneticMaterial(String geneticMaterialX, String geneticMaterialY) {
        Objects.requireNonNull(geneticMaterialX);
        Objects.requireNonNull(geneticMaterialY);
        return new Individ(geneticMaterialX, geneticMaterialY);
    }

    /**
     * метод нужен для определения возможности существования гена с такими параметрами
     * это все из-за особенности мутации + позиционного кодирования.
     */
    public static boolean isValid(String geneticMaterialX, String geneticMaterialY) {
        return Integer.parseInt(geneticMaterialX, 2) <= xIntervalConverter.getNumberOfSteps()
                && Integer.parseInt(geneticMaterialY, 2) <= yIntervalConverter.getNumberOfSteps();
    }

    public double getX() {
        return getXAsDouble();
    }

    public double getY() {
        return getYAsDouble();
    }

    public String getGeneticMaterialX() {
        return geneticMaterialX.toString();
    }

    public String getGeneticMaterialY() {
        return geneticMaterialY.toString();
    }

    public double getFitness() {
        // lazy evaluation for performance
        if (this.fitness == null) {
            // minus for minimization
            this.fitness = -bransinsFunction(getXAsDouble(), getYAsDouble());
        }
        return this.fitness;
    }

    public void mutate() {
        for (int i = 0; i < geneticMaterialX.length(); i++) {
            if (random.nextDouble() < getConfig().getMutationRate()) {
                tryInvertXGen(i);
            }
        }

        for (int i = 0; i < geneticMaterialY.length(); i++) {
            if (random.nextDouble() < getConfig().getMutationRate()) {
                tryInvertYGen(i);
            }
        }
    }

    public void trySetXGen(int index, char bit) {
        StringBuilder xCopy = new StringBuilder(geneticMaterialX);
        xCopy.setCharAt(index, bit);
        if (isValid(xCopy.toString(), geneticMaterialY.toString())) {
            geneticMaterialX.setCharAt(index, bit);
        }
    }

    public void tryInvertXGen(int index) {
        StringBuilder xCopy = new StringBuilder(geneticMaterialX);
        char mutatedBit = (geneticMaterialX.charAt(index) == '0') ? '1' : '0';
        xCopy.setCharAt(index, mutatedBit);
        if (isValid(xCopy.toString(), geneticMaterialY.toString())) {
            geneticMaterialX.setCharAt(index, mutatedBit);
        }
    }

    public void trySetYGen(int index, char bit) {
        StringBuilder yCopy = new StringBuilder(geneticMaterialY);
        yCopy.setCharAt(index, bit);
        if (isValid(geneticMaterialX.toString(), yCopy.toString())) {
            geneticMaterialY.setCharAt(index, bit);
        }
    }
    public void tryInvertYGen(int index) {
        StringBuilder yCopy = new StringBuilder(geneticMaterialY);
        char mutatedBit = (geneticMaterialY.charAt(index) == '0') ? '1' : '0';
        yCopy.setCharAt(index, mutatedBit);
        if (isValid(geneticMaterialX.toString(), yCopy.toString())) {
            geneticMaterialY.setCharAt(index, mutatedBit);
        }
    }

    private double getXAsDouble() {
        return xIntervalConverter.positionToDouble(Integer.parseInt(geneticMaterialX.toString(), 2));
    }

    private double getYAsDouble() {
        return yIntervalConverter.positionToDouble(Integer.parseInt(geneticMaterialY.toString(), 2));
    }

    @Override
    public int compareTo(Individ other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }

    @Override
    public String toString() {
        return "%s %s (%s %s)".formatted(geneticMaterialX, geneticMaterialY, getXAsDouble(), getYAsDouble());
    }

}
