package com.ruslooob.hyper_params;

import com.ruslooob.Configuration;
import com.ruslooob.common.NaturalSelectionStrategyType;
import com.ruslooob.common.ParentSelectionStrategyType;
import com.ruslooob.common.StabilizedGeneticAlgorithmStatistics;
import com.ruslooob.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static com.ruslooob.Configuration.getConfig;

public class GeneticAlgorithmTuner {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmTuner.class);

    // запустить разные версии алгоритма с разными конфигурациями, отчет положить в csv-файл
    public void findBestAlgorithm() {
        var realSolver = new com.ruslooob.real_number.GeneticAlgorithmSolver();
        var positionSolver = new com.ruslooob.position_code.GeneticAlgorithmSolver();

        ExecutorService executor = Executors.newFixedThreadPool(getUsingProcessorsCount());
        BlockingQueue<StabilizedGeneticAlgorithmStatistics> realConfigStatisticsQueue = new LinkedBlockingQueue<>();
        BlockingQueue<StabilizedGeneticAlgorithmStatistics> positionCodeStatisticsQueue = new LinkedBlockingQueue<>();

        List<Configuration> configurations = getConfigurations();
        for (Configuration config : configurations) {
            executor.submit(() -> {
                Configuration.updateConfiguration(config);
                try {
                    StabilizedGeneticAlgorithmStatistics realStatistics = realSolver.solveStabilized();
                    StabilizedGeneticAlgorithmStatistics positionStatistics = positionSolver.solveStabilized();
                    // добавляет только те результаты, которые успешны (на случай невалидной конфигурации)
                    realConfigStatisticsQueue.put(realStatistics);
                    positionCodeStatisticsQueue.put(positionStatistics);
                } catch (Exception e) {
                    log.error("Algorithm error with config {}", config, e);
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(300, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        // Export results
        new StatisticsExporter(/*fixme*/null,/*fixme*/null)
                .exportStabilizedResults(configurations, new ArrayList<>(realConfigStatisticsQueue), new ArrayList<>(positionCodeStatisticsQueue));
    }

    private static int getUsingProcessorsCount() {
        int numberOfProcessors = getConfig().getNumberOfProcessors();
        return  numberOfProcessors == -1
                ? Runtime.getRuntime().availableProcessors()
                : numberOfProcessors;
    }

    // спаси сохрани
    private List<Configuration> getConfigurations() {
        List<Configuration> configurations = new ArrayList<>();

        ParentSelectionStrategyType[] parentSelectionStrategies = {ParentSelectionStrategyType.ROULETTE_WHEEL, ParentSelectionStrategyType.PANMIXIA};
        NaturalSelectionStrategyType[] naturalSelectionStrategies = {NaturalSelectionStrategyType.TRUNCATION, NaturalSelectionStrategyType.TOURNAMENT};
        int[] individualsCounts = {50, 100, 200};
        double[] mutationRates = {0.01, 0.1};
        double[] recombinationRates = {0.1, 0.3, 0.5, 0.7, 0.9}; // Example values
        double[] perturbationRanges = {0.05, 0.1, 0.2}; // Example values
        double[] truncationThresholds = {0.3, 0.5, 0.7}; // Example values

        for (ParentSelectionStrategyType parentSelection : parentSelectionStrategies) {
            for (NaturalSelectionStrategyType naturalSelection : naturalSelectionStrategies) {
                for (int individualsCount : individualsCounts) {
                    for (double mutationRate : mutationRates) {
                        for (double recombinationRate : recombinationRates) {
                            for (double perturbationRange : perturbationRanges) {
                                for (double truncationThreshold : truncationThresholds) {
                                    Configuration config = new Configuration()
                                            .setParentsSelectionStrategy(parentSelection)
                                            .setNaturalSelectionStrategy(naturalSelection)
                                            .setIndividualsInPopulationCount(individualsCount)
                                            .setMutationRate(mutationRate)
                                            .setRecombinationRate(recombinationRate)
                                            .setPerturbationRange(perturbationRange);

                                    if (naturalSelection == NaturalSelectionStrategyType.TRUNCATION) {
                                        config.setTruncationThreshold(truncationThreshold);
                                    }

                                    configurations.add(config);
                                }
                            }
                        }
                    }
                }
            }
        }

        return configurations;
    }

}
