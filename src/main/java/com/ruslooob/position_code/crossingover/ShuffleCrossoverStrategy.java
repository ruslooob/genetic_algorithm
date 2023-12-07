package com.ruslooob.position_code.crossingover;

import com.ruslooob.common.Pair;
import com.ruslooob.position_code.model.Individ;
import com.ruslooob.position_code.model.Parents;

import java.util.Objects;

public class ShuffleCrossoverStrategy implements RecombinationStrategy {
    private final Parents parents;

    public ShuffleCrossoverStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
    }

    @Override//todo подумать над выносом в individual
    public Pair<Individ> recombine() {
        Individ parent1 = parents.getFirstParent();
        Individ parent2 = parents.getSecondParent();

        String binaryGeneX1 = parent1.getGeneticMaterialX();
        String binaryGeneY1 = parent1.getGeneticMaterialY();
        String binaryGeneX2 = parent2.getGeneticMaterialX();
        String binaryGeneY2 = parent2.getGeneticMaterialY();

        int crossoverPointX = generateRandomCrossoverPoint(parent1.getGeneticMaterialX().length());
        String childBinaryGeneX1 = binaryGeneX1.substring(0, crossoverPointX) + binaryGeneX2.substring(crossoverPointX);
        String childBinaryGeneX2 = binaryGeneX2.substring(0, crossoverPointX) + binaryGeneX1.substring(crossoverPointX);

        int crossoverPointY = generateRandomCrossoverPoint(binaryGeneY1.length());
        String childBinaryGeneY1 = binaryGeneY1.substring(0, crossoverPointY) + binaryGeneY2.substring(crossoverPointY);
        String childBinaryGeneY2 = binaryGeneY2.substring(0, crossoverPointY) + binaryGeneY1.substring(crossoverPointY);

        Individ child1 = null;
        if (Individ.isValid(childBinaryGeneX1, childBinaryGeneY1)) {
            child1 = Individ.fromGeneticMaterial(childBinaryGeneX1, childBinaryGeneY1);
            child1.mutate();
        }

        Individ child2 = null;
        if (Individ.isValid(childBinaryGeneX2, childBinaryGeneY2)) {
            child2 = Individ.fromGeneticMaterial(childBinaryGeneX2, childBinaryGeneY2);
            child2.mutate();
        }

        return new Pair<>(child1, child2);
    }

    private int generateRandomCrossoverPoint(int max) {
        return (int) (Math.random() * max);
    }

}
