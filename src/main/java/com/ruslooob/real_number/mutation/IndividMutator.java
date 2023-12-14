package com.ruslooob.real_number.mutation;

import com.ruslooob.real_number.model.Individ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;

import static com.ruslooob.Configuration.getConfig;

public class IndividMutator {
    private static final Logger log = LoggerFactory.getLogger(IndividMutator.class);
    private static final double MUTATION_RATE = getConfig().getMutationRate();
    private static final double PERTURBATION_RANGE = getConfig().getPerturbationRange();

    private final Individ[] individuals;

    public IndividMutator(Individ... individuals) {
        this.individuals = individuals;
    }

    public void mutate() {
        for (Individ individ : individuals) {
            if (individ != null) {
                mutateIndivid(individ);
            }
        }
    }

//    private void mutateIndivid(Individ individ) {
//        Random random = new SecureRandom();
//
//        for (int i = 0; i < individ.getGeneticMaterial().length; i++) {
//            if (random.nextDouble() < MUTATION_RATE) {
//                double mutatedValue = random.nextDouble() * 2 * PERTURBATION_RANGE - PERTURBATION_RANGE;
//                double newValue = individ.getGeneticMaterial()[i] + mutatedValue;
//
//                // Ensure the mutated value stays within the specified interval
//                if (i == 0) {
//                    // Check bounds for x
//                    newValue = Math.max(newValue, getConfig().getxInterval().start());
//                    newValue = Math.min(newValue, getConfig().getxInterval().end());
//                } else if (i == 1) {
//                    // Check bounds for y
//                    newValue = Math.max(newValue, getConfig().getyInterval().start());
//                    newValue = Math.min(newValue, getConfig().getyInterval().end());
//                }
//
//                log.info("Mutation individ before: {}", individ);
//                individ.setGen(i, newValue);
//                log.info("Mutation individ after: {}", individ);
//            }
//        }
//    }

    private void mutateIndivid(Individ individ) {
        Random random = new SecureRandom();

        for (int i = 0; i < individ.getGeneticMaterial().length; i++) {
            if (random.nextDouble() < MUTATION_RATE) {
                double oldValue = individ.getGeneticMaterial()[i];
                double mutatedValue;

                do {
                    mutatedValue = oldValue + (random.nextDouble() * 2 * PERTURBATION_RANGE - PERTURBATION_RANGE);
                } while (!isValidMutation(mutatedValue, i));

                log.info("Mutation individ before: {}", individ);
                individ.setGen(i, mutatedValue);
                log.info("Mutation individ after: {}", individ);
            }
        }
    }

    private boolean isValidMutation(double mutatedValue, int genIndex) {
        if (genIndex == 0) {  // Assuming genIndex 0 is for X
            return getConfig().getxInterval().contains(mutatedValue);
        } else if (genIndex == 1) {  // Assuming genIndex 1 is for Y
            return getConfig().getyInterval().contains(mutatedValue);
        }
        return false;
    }
}
