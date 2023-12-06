package com.ruslooob.model;

import com.ruslooob.Configuration;

import java.util.Arrays;

import static com.ruslooob.common.Functions.bransinsFunction;

public class Individ implements Comparable<Individ> {
    private final double[] geneticMaterial;
    private Double fitness;

    private Individ(double[] geneticMaterial) {
        this.geneticMaterial = geneticMaterial;
    }

    public static Individ fromGeneticMaterial(double[] geneticMaterial) {
        if (geneticMaterial.length != Configuration.DIMENSIONS) {
            throw new IllegalArgumentException("Invalid dimensions passed while construct Individ instance %s"
                    .formatted(Arrays.toString(geneticMaterial)));
        }
        return new Individ(geneticMaterial);
    }

    public void setGen(int index, double genValue) {
        this.geneticMaterial[index] = genValue;
    }

    public double getFitness() {
        // lazy evaluation for performance
        if (this.fitness == null) {
            // minus for minimization
            this.fitness = -bransinsFunction(geneticMaterial[0], geneticMaterial[1]);
        }
        return this.fitness;
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
