package com.ruslooob.real_number.model;

import com.ruslooob.Configuration;
import com.ruslooob.common.DoubleInterval;

import java.util.Arrays;
import java.util.Objects;

import static com.ruslooob.Configuration.getConfig;
import static com.ruslooob.common.Functions.bransinsFunction;

public class Individ implements Comparable<Individ> {
    private static final DoubleInterval xInterval = getConfig().getxInterval();
    private static final DoubleInterval yInterval = getConfig().getyInterval();
    private final double[] geneticMaterial;
    private Double fitness;

    private Individ(double[] geneticMaterial) {
        this.geneticMaterial = geneticMaterial;
        if (!xInterval.contains(geneticMaterial[0]) || !yInterval.contains(geneticMaterial[1])) {
            throw new IllegalArgumentException("incorrect genetic material: %s".formatted(Arrays.toString(geneticMaterial)));
        }
    }

    public static Individ fromGeneticMaterial(double[] geneticMaterial) {
        Objects.requireNonNull(geneticMaterial);
        if (geneticMaterial.length != getConfig().getDimensions()) {
            throw new IllegalArgumentException("Invalid dimensions passed while construct Individ instance %s"
                    .formatted(Arrays.toString(geneticMaterial)));
        }
        return new Individ(geneticMaterial);
    }

    public void setGen(int index, Double genValue) {
        this.geneticMaterial[index] = Objects.requireNonNull(genValue);
    }

    public double getFitness() {
        // lazy evaluation for performance
        if (this.fitness == null) {
            // minus for minimization
            this.fitness = -bransinsFunction(geneticMaterial[0], geneticMaterial[1]);
        }
        return this.fitness;
    }

    public double getX() {
        return geneticMaterial[0];
    }

    public double getY() {
        return geneticMaterial[1];
    }

    public double[] getGeneticMaterial() {
        return this.geneticMaterial;
    }

    @Override
    public int compareTo(Individ other) {
        return Double.compare(this.getFitness(), other.getFitness());
    }

    @Override
    public String toString() {
        return Arrays.toString(geneticMaterial);
    }
}
