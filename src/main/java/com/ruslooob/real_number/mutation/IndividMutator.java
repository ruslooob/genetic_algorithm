package com.ruslooob.real_number.mutation;

import com.ruslooob.Configuration;
import com.ruslooob.real_number.model.Individ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;

public class IndividMutator {
    private static final Logger log = LoggerFactory.getLogger(IndividMutator.class);
    private static final double MUTATION_RATE = Configuration.MUTATION_RATE;
    private static final double PERTURBATION_RANGE = Configuration.PERTURBATION_RANGE;

    private final Individ[] individuals;

    public IndividMutator(Individ... individuals) {
        this.individuals = individuals;
    }

    public void mutate() {
        for (Individ individ : individuals) {
            mutateIndivid(individ);
        }
    }

    private void mutateIndivid(Individ individ) {
        Random random = new SecureRandom();

        for (int i = 0; i < individ.getGeneticMaterial().length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                double mutatedValue = random.nextDouble() * 2 * PERTURBATION_RANGE - PERTURBATION_RANGE;
                log.info("Mutation individ before: {}", individ);
                individ.setGen(i, mutatedValue);
                log.info("Mutation individ after: {}", individ);
            }
        }
    }
}