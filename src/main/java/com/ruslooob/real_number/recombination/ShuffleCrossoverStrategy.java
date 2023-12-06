package com.ruslooob.real_number.recombination;

import com.ruslooob.real_number.model.Individ;
import com.ruslooob.real_number.common.Pair;
import com.ruslooob.real_number.model.Parents;

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
