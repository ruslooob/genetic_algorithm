package com.ruslooob.real_number.parent_selection;

import com.ruslooob.real_number.model.GenerationPool;
import com.ruslooob.real_number.model.Individ;
import com.ruslooob.real_number.model.Parents;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class PanmixiaSelectionStrategy implements SelectionStrategy {
    private final GenerationPool generationPool;
    private final Random random = new Random();

    public PanmixiaSelectionStrategy(GenerationPool generationPool) {
        this.generationPool = Objects.requireNonNull(generationPool);
    }

    @Override
    public Parents selectParents() {
        List<Individ> individuals = generationPool.getIndividuals();

        if (individuals.size() < 2) {
            throw new IllegalArgumentException("Insufficient individuals for Panmixia selection");
        }

        // Randomly select two distinct parents
        Individ parent1 = selectRandomIndividual(individuals);
        Individ parent2 = selectRandomIndividual(individuals, parent1);

        return new Parents(parent1, parent2);
    }

    private Individ selectRandomIndividual(List<Individ> individuals) {
        int randomIndex = random.nextInt(individuals.size());
        return individuals.get(randomIndex);
    }

    private Individ selectRandomIndividual(List<Individ> individuals, Individ excludedIndividual) {
        List<Individ> filteredIndividuals = individuals.stream()
                .filter(individ -> !individ.equals(excludedIndividual))
                .collect(Collectors.toList());

        return selectRandomIndividual(filteredIndividuals);
    }
}
