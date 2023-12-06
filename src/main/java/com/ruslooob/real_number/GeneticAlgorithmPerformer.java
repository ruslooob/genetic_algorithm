package com.ruslooob.real_number;

import com.ruslooob.Configuration;
import com.ruslooob.real_number.common.Pair;
import com.ruslooob.real_number.model.GenerationPool;
import com.ruslooob.real_number.model.GeneticAlgorithmStatistics;
import com.ruslooob.real_number.model.Individ;
import com.ruslooob.real_number.model.Parents;
import com.ruslooob.real_number.mutation.IndividMutator;
import com.ruslooob.real_number.natural_selection.TruncationNaturalSelection;
import com.ruslooob.real_number.parent_selection.RouletteWheelSelectionStrategy;
import com.ruslooob.real_number.recombination.IntermediateRecombinationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ruslooob.real_number.util.RandomUtils.createRandomIndividuals;

// todo добавить добавить возможно создавать экземпляр с различными параметрами окружения для возможности подбора гипер-параметров
public class GeneticAlgorithmPerformer {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmPerformer.class);

    private static final int REPRODUCTIONS_PER_GENERATION_COUNT = (int) (Configuration.RECOMBINATION_RATE * Configuration.INDIVIDUALS_IN_POPULATION_COUNT);

    private final GenerationPool generationPool = new GenerationPool(createRandomIndividuals(Configuration.INDIVIDUALS_IN_POPULATION_COUNT));
    private int generationNumber = 0;
    private Individ bestIndivid;

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

        this.bestIndivid = generationPool.getMostFitIndivid();
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

    public GeneticAlgorithmStatistics getStatistics() {
        return new GeneticAlgorithmStatistics(generationNumber, bestIndivid);
    }
}
