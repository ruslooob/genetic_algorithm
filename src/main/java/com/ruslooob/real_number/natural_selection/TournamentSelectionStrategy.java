package com.ruslooob.real_number.natural_selection;

import com.ruslooob.real_number.model.GenerationPool;
import com.ruslooob.real_number.model.Individ;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.ruslooob.Configuration.getConfig;

public class TournamentSelectionStrategy implements NaturalSelectionStrategy {
    private final GenerationPool generationPool;
    private final Random random = new Random();
    private final int tournamentSize;

    public TournamentSelectionStrategy(GenerationPool generationPool, int tournamentSize) {
        this.generationPool = Objects.requireNonNull(generationPool);
        this.tournamentSize = Math.max(1, tournamentSize); // Ensure tournament size is at least 1
    }

    @Override
    public void filterGenerationPool() {
        List<Individ> individuals = generationPool.getIndividuals();

        if (individuals.size() < 2) {
            throw new IllegalArgumentException("Insufficient individuals for Tournament selection");
        }

        List<Individ> selectedIndividuals = new ArrayList<>();

        for (int i = 0; i < getConfig().getIndividualsInPopulationCount(); i++) {
            // Perform tournament selection
            Individ selectedIndivid = performTournament(individuals);
            selectedIndividuals.add(selectedIndivid);
        }

        // Replace the generation pool with the selected individuals
        generationPool.setIndividuals(selectedIndividuals);
    }

    private Individ performTournament(List<Individ> individuals) {
        int tournamentSize = Math.min(this.tournamentSize, individuals.size());
        List<Individ> tournamentParticipants = new ArrayList<>();

        // Randomly select individuals for the tournament
        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(individuals.size());
            tournamentParticipants.add(individuals.get(randomIndex));
        }

        // Return the most fit individual from the tournament
        return tournamentParticipants.stream()
                .max(Individ::compareTo)
                .orElseThrow();
    }
}