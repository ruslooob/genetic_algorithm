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

public class GeneticAlgorithmTuner {
    private static final Logger log = LoggerFactory.getLogger(GeneticAlgorithmTuner.class);

    // запустить разные версии алгоритма с разными конфигурациями, отчет положить в csv-файл
    public void findBestAlgorithm() {
        var realSolver = new com.ruslooob.real_number.GeneticAlgorithmSolver();
        var positionSolver = new com.ruslooob.position_code.GeneticAlgorithmSolver();
        List<Configuration> configurations = getConfigurations();
        List<StabilizedGeneticAlgorithmStatistics> realConfigStatistics = new ArrayList<>();
        List<StabilizedGeneticAlgorithmStatistics> positionCodeStatistics = new ArrayList<>();
        for (var config : configurations) {
            // ИЗМЕНЕНИЕ ГЛОБАЛЬНОЙ ПЕРЕМЕННОЙ
            Configuration.updateConfiguration(config);
            try {
                StabilizedGeneticAlgorithmStatistics realStatistics = realSolver.solveStabilized();
                StabilizedGeneticAlgorithmStatistics positionStatistics = positionSolver.solveStabilized();
                /*добавляет только те результаты, которые успешны (на случай невалидной конфигурации)*/
                realConfigStatistics.add(realStatistics);
                positionCodeStatistics.add(positionStatistics);
            } catch (Exception e) {
                log.error("algorithm error with config {}", config, e);
            }
        }

        new StatisticsExporter(/*fixme*/null,/*fixme*/null)
                .exportStabilizedResults(configurations, realConfigStatistics, positionCodeStatistics);
        log.info("{}", realConfigStatistics);
        log.info("{}", positionCodeStatistics);
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


//        // 50 individuals
//        // roulette wheel, truncation selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // roulette wheel, tournament selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // roulette wheel, truncation selection, big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1)));
//        // roulette whell, tournament selection, big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        // panmixia, truncation selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // panmixia, tournament selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.01));
//        // panmixia, truncation selection big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1)));
//        // panmixia, tournament selection big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(50)
//                .setMutationRate(0.1));
//        /// 200 individuals
//        // roulette wheel, truncation selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // roulette wheel, tournament selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // roulette wheel, truncation selection, big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200)));
//        // roulette wheel, tournament selection, big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.ROULETTE_WHEEL)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));
//        // panmixia, truncation selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setIndividualsInPopulationCount(200));
//        // panmixia, tournament selection, small mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setIndividualsInPopulationCount(200)
//                .setMutationRate(0.01));
//        // panmixia, truncation selection big mutation
//        configurations.add((new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TRUNCATION)
//                .setMutationRate(0.1))
//                .setIndividualsInPopulationCount(200));
//        // panmixia, tournament selection big mutation
//        configurations.add(new Configuration()
//                .setParentsSelectionStrategy(ParentSelectionStrategyType.PANMIXIA)
//                .setNaturalSelectionStrategy(NaturalSelectionStrategyType.TOURNAMENT)
//                .setMutationRate(0.1)
//                .setIndividualsInPopulationCount(200));

        return configurations;
    }

}
