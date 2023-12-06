package com.ruslooob.recombination;

import com.ruslooob.Configuration;
import com.ruslooob.common.DoubleInterval;
import com.ruslooob.common.Pair;
import com.ruslooob.model.Individ;
import com.ruslooob.model.Parents;

import java.util.Objects;

import static com.ruslooob.util.RandomUtils.generateRandomNumber;

public class IntermediateRecombinationStrategy implements RecombinationStrategy {
    private static final int DIMENSIONS = Configuration.DIMENSIONS;
    private static final double D = Configuration.INTERMEDIATE_D_CONSTANT;
    private static final DoubleInterval alphaInterval = new DoubleInterval(-D, 1 + D);
    private static final int PARENT_SIZE = 2;

    private final Parents parents;
    private final int geneticMaterialSize;

    public IntermediateRecombinationStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
        this.geneticMaterialSize = parents.getFirstParent().getGeneticMaterial().length;
    }

    @Override
    public Pair<Individ> recombine() {
        double[][] generatedSchema = generateSchema();

        double[][] parentGeneticMaterials = new double[][]{
                parents.getFirstParent().getGeneticMaterial(),
                parents.getSecondParent().getGeneticMaterial()
        };

        double[][] childGeneticMaterials = new double[parentGeneticMaterials.length][parentGeneticMaterials[0].length];
        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < generatedSchema[0].length; j++) {
                childGeneticMaterials[i][j] = evalNewIndividGen(parentGeneticMaterials[0][j], parentGeneticMaterials[1][j], generatedSchema[i][j]);
            }
        }

        return new Pair<>(
                Individ.fromGeneticMaterial(childGeneticMaterials[0]),
                Individ.fromGeneticMaterial(childGeneticMaterials[1]));
    }

    private double[][] generateSchema() {
        double[][] schema = new double[DIMENSIONS][geneticMaterialSize];

        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < geneticMaterialSize; j++) {
                schema[i][j] = generateRandomNumber(alphaInterval);
            }
        }
        return schema;
    }

    private double evalNewIndividGen(double firstParentGen, double secondParentGen, double alpha) {
        return firstParentGen + alpha * (secondParentGen - firstParentGen);
    }
}
