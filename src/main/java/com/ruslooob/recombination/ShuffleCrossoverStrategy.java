package com.ruslooob.recombination;

import com.ruslooob.model.Individ;
import com.ruslooob.common.Pair;
import com.ruslooob.model.Parents;

import java.util.Objects;

public class ShuffleCrossoverStrategy implements RecombinationStrategy {
    private final Parents parents;

    public ShuffleCrossoverStrategy(Parents parents) {
        this.parents = Objects.requireNonNull(parents);
    }

    @Override
    public Pair<Individ> recombine() {
        return null;
    }
}
