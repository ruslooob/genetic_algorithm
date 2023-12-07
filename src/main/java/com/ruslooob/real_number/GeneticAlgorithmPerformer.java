package com.ruslooob.real_number;

import com.ruslooob.Configuration;
import com.ruslooob.common.GeneticAlgorithmStatistics;
import com.ruslooob.common.Pair;
import com.ruslooob.common.Point2D;
import com.ruslooob.real_number.model.GenerationPool;
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
            log.info("Covergence reached");
            return true;
        }

        return false;
    }

    private double getErrorRate(Individ target) {
        var solutions = new Point2D[] {
                new Point2D(Math.PI, 2.275),
                new Point2D(-Math.PI, 12.275),
                new Point2D(9.42478, 2.2475)
        };

        var targetPoint = new Point2D(target.getX(), target.getY());
        var nearestPoint = solutions[0];
        double minDistance = calculateDistance(targetPoint, nearestPoint);

        for (int i = 1; i < solutions.length; i++) {
            double distance = calculateDistance(targetPoint, solutions[i]);
            if (distance < minDistance) {
                minDistance = distance;
                nearestPoint = solutions[i];
            }
        }

        return minDistance;
    }

    private static double calculateDistance(Point2D point1, Point2D point2) {
        double xDiff = point2.x() - point1.x();
        double yDiff = point2.y() - point1.y();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public GeneticAlgorithmStatistics getStatistics() {
        return new GeneticAlgorithmStatistics(generationNumber, getErrorRate(bestIndivid));
    }
}
