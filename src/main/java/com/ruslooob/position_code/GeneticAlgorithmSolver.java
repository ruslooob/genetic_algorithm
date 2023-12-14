package com.ruslooob.position_code;

import com.ruslooob.common.Pair;
import com.ruslooob.common.Point2D;
import com.ruslooob.common.StabilizedGeneticAlgorithmStatistics;
import com.ruslooob.position_code.crossingover.ShuffleCrossoverStrategy;
import com.ruslooob.position_code.model.GenerationPool;
import com.ruslooob.common.GeneticAlgorithmStatistics;
import com.ruslooob.position_code.model.Individ;
import com.ruslooob.position_code.model.Parents;
import com.ruslooob.position_code.natural_selection.TruncationNaturalSelection;
import com.ruslooob.position_code.parent_selection.RouletteWheelSelectionStrategy;
import com.ruslooob.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static com.ruslooob.Configuration.getConfig;
import static com.ruslooob.position_code.util.RandomUtils.createRandomIndividuals;


// todo добавить добавить возможно создавать экземпляр с различными параметрами окружения для возможности подбора гипер-параметров
public class GeneticAlgorithmSolver {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmSolver.class);

    private static final int REPRODUCTIONS_PER_GENERATION_COUNT = (int) (getConfig().getRecombinationRate() * getConfig().getIndividualsInPopulationCount());
    private final GenerationPool generationPool = new GenerationPool(createRandomIndividuals(getConfig().getIndividualsInPopulationCount()));
    private int generationNumber = 0;
    private Individ bestIndivid;

    public GeneticAlgorithmSolver() {
    }

    public StabilizedGeneticAlgorithmStatistics solveStabilized() {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();
        var config = getConfig();

        for (int i = 0; i < getConfig().getNumberOfRuns(); i++) {
            log.info("\nRUN#{}", i + 1);
            var geneticAlgorithm = new GeneticAlgorithmSolver();
            geneticAlgorithm.solve();
            statistics.add(geneticAlgorithm.getStatistics());
        }

        log.info("=====Algo statistics=====");
        int averageGenerations = (int) calculateAverage(statistics, GeneticAlgorithmStatistics::generationNumberCount);
        double averageErrorRate = calculateAverage(statistics, GeneticAlgorithmStatistics::errorRate);
        int errorRuns = (int) statistics.stream().filter(stat -> stat.errorRate() > 0.5).count();
        log.info("Average number of generation to find optimum: {}", averageGenerations);
        log.info("Mean error rate: {}", averageErrorRate);
        log.info("Error runs: {}", errorRuns);
        return new StabilizedGeneticAlgorithmStatistics(averageGenerations, averageErrorRate, errorRuns);
//        new StatisticsExporter(config, "position_code")
//                .export(averageGenerations, averageErrorRate);
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }

    public void solve() {
        while (!isStopCriteriaAcquired()) {
            for (int i = 0; i < REPRODUCTIONS_PER_GENERATION_COUNT; i++) {
                Parents parents = new RouletteWheelSelectionStrategy(generationPool)
                        .selectParents();
                Pair<Individ> children = new ShuffleCrossoverStrategy(parents)
                        .recombine();
                if (children.first() != null) {
                    generationPool.addIndividuals(children.first());
                }
                if (children.second() != null) {
                    generationPool.addIndividuals(children.second());
                }
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
        if (generationNumber == getConfig().getMaxGenerationsCount()) {
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
