package com.ruslooob.real_number.recombination;

import com.ruslooob.common.DoubleInterval;
import com.ruslooob.common.Pair;
import com.ruslooob.real_number.model.Individ;
import com.ruslooob.real_number.model.Parents;
import com.ruslooob.real_number.util.RandomUtils;

import java.util.Objects;

import static com.ruslooob.Configuration.getConfig;

public class IntermediateRecombinationStrategy implements RecombinationStrategy {
    private static final DoubleInterval xInterval = getConfig().getxInterval();
    private static final DoubleInterval yInterval = getConfig().getyInterval();
    private static final int DIMENSIONS = getConfig().getDimensions();
    private static final double D = getConfig().getIntermediateDConstant();
    private static final DoubleInterval ALPHA_INTERVAL = new DoubleInterval(-D, 1 + D);
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

        var parentGeneticMaterials = new double[][]{
                parents.getFirstParent().getGeneticMaterial(),
                parents.getSecondParent().getGeneticMaterial()
        };

        var childGeneticMaterials = new double[parentGeneticMaterials.length][parentGeneticMaterials[0].length];
        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < generatedSchema[0].length; j++) {
                childGeneticMaterials[i][j] = evalNewIndividGen(parentGeneticMaterials[0][j], parentGeneticMaterials[1][j], generatedSchema[i][j]);
            }
        }

        Individ firstChild = null;
        if (isValidGeneticMaterial(childGeneticMaterials[0])) {
            firstChild = Individ.fromGeneticMaterial(childGeneticMaterials[0]);
        }

        Individ secondChild = null;
        if (isValidGeneticMaterial(childGeneticMaterials[1])) {
            secondChild = Individ.fromGeneticMaterial(childGeneticMaterials[1]);
        }
        return new Pair<>(
                firstChild,
                secondChild);
    }

    private double[][] generateSchema() {
        double[][] schema = new double[DIMENSIONS][geneticMaterialSize];

        for (int i = 0; i < PARENT_SIZE; i++) {
            for (int j = 0; j < geneticMaterialSize; j++) {
                schema[i][j] = RandomUtils.generateRandomNumber(ALPHA_INTERVAL);
            }
        }
        return schema;
    }

    private double evalNewIndividGen(double firstParentGen, double secondParentGen, double alpha) {
        return firstParentGen + alpha * (secondParentGen - firstParentGen);
    }

    private boolean isValidGeneticMaterial(double[] geneticMaterial) {
        return xInterval.contains(geneticMaterial[0]) && yInterval.contains(geneticMaterial[1]);
    }
}
