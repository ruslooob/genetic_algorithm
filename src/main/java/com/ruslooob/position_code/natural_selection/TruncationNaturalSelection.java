package com.ruslooob.position_code.natural_selection;

import com.ruslooob.position_code.model.GenerationPool;
import com.ruslooob.position_code.model.Individ;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ruslooob.Configuration.getConfig;

public class TruncationNaturalSelection implements NaturalSelectionStrategy {
    private static final double TRUNCATION_THRESHOLD = getConfig().getTruncationThreshold();
    private final GenerationPool generationPool;


    public TruncationNaturalSelection(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public void filterGenerationPool() {
        List<Individ> selectedIndividuals = generationPool.getIndividuals().stream()
                .sorted(Comparator.reverseOrder())
                .limit((long) (TRUNCATION_THRESHOLD * generationPool.getSize()))
                .collect(Collectors.toList());

        generationPool.setIndividuals(selectedIndividuals);
    }
}
