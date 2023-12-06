package com.ruslooob;

import com.ruslooob.common.Pair;
import com.ruslooob.model.GenerationPool;
import com.ruslooob.model.Individ;
import com.ruslooob.model.Parents;
import com.ruslooob.mutation.IndividMutator;
import com.ruslooob.natural_selection.TruncationNaturalSelection;
import com.ruslooob.parent_selection.RouletteWheelSelectionStrategy;
import com.ruslooob.recombination.IntermediateRecombinationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ruslooob.util.RandomUtils.createRandomIndividuals;

// todo добавить добавить возможно создавать экземпляр с различными параметрами окружения для возможности подбора гипер-параметров
public class GeneticAlgorithmPerformer {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmPerformer.class);

    private static final int REPRODUCTIONS_PER_GENERATION_COUNT = (int) (Configuration.RECOMBINATION_RATE * Configuration.INDIVIDUALS_IN_POPULATION_COUNT);

    private final GenerationPool generationPool = new GenerationPool(createRandomIndividuals(Configuration.INDIVIDUALS_IN_POPULATION_COUNT));
    private int generationNumber = 0;

    public void start() {
        while (!isStopCriteriaAcquired()) {
            for (int i = 0; i < REPRODUCTIONS_PER_GENERATION_COUNT; i++) {
                Parents parents = new RouletteWheelSelectionStrategy(generationPool)
                        .selectParents();

                Pair<Individ> children = new IntermediateRecombinationStrategy(parents)
                        .recombine();

                new IndividMutator(children.first(), children.second())
                        .mutate();

                generationPool.addIndividuals(children.first(), children.second());
            }

            new TruncationNaturalSelection(generationPool)
                    .filterGenerationPool();

            generationNumber++;

            Individ bestIndivid = generationPool.getMostFitIndivid();
            log.info("step {} most fit individ: {}, fit={}", generationNumber, bestIndivid, bestIndivid.getFitness());
        }
    }

    private boolean isStopCriteriaAcquired() {
        if (generationNumber == Configuration.MAX_GENERATIONS_COUNT) {
            return true;
        }

        if (generationPool.hasConverged()) {
            return true;
        }

        return false;
    }
}
