package com.ruslooob.real_number;

import com.ruslooob.Configuration;
import com.ruslooob.common.GeneticAlgorithmStatistics;
import com.ruslooob.common.Pair;
import com.ruslooob.common.Point2D;
import com.ruslooob.real_number.model.GenerationPool;
import com.ruslooob.real_number.model.Individ;
import com.ruslooob.real_number.model.Parents;
import com.ruslooob.real_number.mutation.IndividMutator;
import com.ruslooob.real_number.natural_selection.NaturalSelectionStrategy;
import com.ruslooob.real_number.natural_selection.TournamentSelectionStrategy;
import com.ruslooob.real_number.natural_selection.TruncationNaturalSelection;
import com.ruslooob.real_number.parent_selection.PanmixiaSelectionStrategy;
import com.ruslooob.real_number.parent_selection.RouletteWheelSelectionStrategy;
import com.ruslooob.real_number.parent_selection.SelectionStrategy;
import com.ruslooob.real_number.recombination.IntermediateRecombinationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.ruslooob.Configuration.getConfig;
import static com.ruslooob.real_number.util.RandomUtils.createRandomIndividuals;

// todo добавить добавить возможно создавать экземпляр с различными параметрами окружения для возможности подбора гипер-параметров
public class GeneticAlgorithmSolver {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmSolver.class);

    private final int reproductionsPerGenerationCount = (int) (getConfig().getRecombinationRate() * getConfig().getIndividualsInPopulationCount());

    private final GenerationPool generationPool = new GenerationPool(createRandomIndividuals(getConfig().getIndividualsInPopulationCount()));
    private int generationNumber = 0;
    private Individ bestIndivid;

    private static double calculateDistance(Point2D point1, Point2D point2) {
        double xDiff = point2.x() - point1.x();
        double yDiff = point2.y() - point1.y();
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public void solve() {
        while (!isStopCriteriaAcquired()) {
            for (int i = 0; i < reproductionsPerGenerationCount; i++) {
                Parents parents = getParentSelectionStrategy(generationPool)
                        .selectParents();

                Pair<Individ> children = new IntermediateRecombinationStrategy(parents)
                        .recombine();

                new IndividMutator(children.first(), children.second())
                        .mutate();

                if (children.first() != null) {
                    generationPool.addIndivid(children.first());
                }
                if (children.second() != null) {
                    generationPool.addIndivid(children.second());
                }
            }

            getNaturalSelectionStrategy(generationPool)
                    .filterGenerationPool();

            generationNumber++;

            Individ bestIndivid = generationPool.getMostFitIndivid();
            log.info("step {} most fit individ: {}, fit={}", generationNumber, bestIndivid, bestIndivid.getFitness());
        }

        this.bestIndivid = generationPool.getMostFitIndivid();
    }

    private boolean isStopCriteriaAcquired() {
        if (generationNumber == getConfig().getMaxGenerationsCount()) {
            return true;
        }

        if (generationPool.hasConverged()) {
            log.info("Covergence reached");
            return true;
        }

        return false;
    }

    private SelectionStrategy getParentSelectionStrategy(GenerationPool pool) {
        var configStrategy = getConfig().getParentsSelectionStrategy();
        return switch (configStrategy) {
            case PANMIXIA -> new PanmixiaSelectionStrategy(pool);
            case ROULETTE_WHEEL -> new RouletteWheelSelectionStrategy(pool);
        };
    }

    private NaturalSelectionStrategy getNaturalSelectionStrategy(GenerationPool pool) {
        var configStrategy = getConfig().getNaturalSelectionStrategy();
        return switch (configStrategy) {
            case TOURNAMENT -> new TournamentSelectionStrategy(pool, 5);//5 is hardcoded tournament size
            case TRUNCATION -> new TruncationNaturalSelection(pool);
        };
    }

    public Individ getBestIndivid() {
        return bestIndivid;
    }

    private double getErrorRate(Individ target) {
        var solutions = new Point2D[]{
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

    public GeneticAlgorithmStatistics getStatistics() {
        return new GeneticAlgorithmStatistics(generationNumber, getErrorRate(bestIndivid));
    }

}
