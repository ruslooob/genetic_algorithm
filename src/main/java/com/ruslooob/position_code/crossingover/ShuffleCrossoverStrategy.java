package com.ruslooob.position_code.crossingover;

import com.ruslooob.common.Pair;
import com.ruslooob.position_code.model.Individ;
import com.ruslooob.position_code.model.Parents;

import java.security.SecureRandom;
import java.util.Objects;

public class ShuffleCrossoverStrategy implements RecombinationStrategy {
    private final Parents parents;

    public ShuffleCrossoverStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
    }

    @Override//todo подумать над выносом в individual & return List instead of Pair to avoid null issues
    public Pair<Individ> recombine() {
        Individ parent1 = parents.getFirstParent();
        Individ parent2 = parents.getSecondParent();

//        swapRandomBit(parent1, parent2);

        String binaryGeneX1 = parent1.getGeneticMaterialX();
        String binaryGeneX2 = parent2.getGeneticMaterialX();

        int crossoverPointX = generateRandomCrossoverPoint(binaryGeneX1.length());
        String childBinaryGeneX1 = binaryGeneX1.substring(0, crossoverPointX) + binaryGeneX2.substring(crossoverPointX);
        String childBinaryGeneX2 = binaryGeneX2.substring(0, crossoverPointX) + binaryGeneX1.substring(crossoverPointX);

        String binaryGeneY1 = parent1.getGeneticMaterialY();
        String binaryGeneY2 = parent2.getGeneticMaterialY();
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

    // функция нигде не используется, но в лекции есть совет ее использовать.
    // с ней результаты у алгоритма хуже, чем без нее
    private void swapRandomBit(Individ parent1, Individ parent2) {
        var random = new SecureRandom();
        int randomXBitIdx = random.nextInt(parent1.getGeneticMaterialX().length() - 1);
        char tempXBit = parent1.getGeneticMaterialX().charAt(randomXBitIdx);
        parent1.trySetXGen(randomXBitIdx, parent2.getGeneticMaterialX().charAt(randomXBitIdx));
        parent2.trySetXGen(randomXBitIdx, tempXBit);

        int randomYBitIdx = random.nextInt(parent1.getGeneticMaterialY().length() - 1);
        char tempYBit = parent1.getGeneticMaterialY().charAt(randomYBitIdx);
        parent1.trySetYGen(randomYBitIdx, parent2.getGeneticMaterialY().charAt(randomYBitIdx));
        parent2.trySetYGen(randomYBitIdx, tempYBit);
    }


    private int generateRandomCrossoverPoint(int max) {
        return (int) (Math.random() * max);
    }

}
