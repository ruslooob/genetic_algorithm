package com.ruslooob.real_number;

import com.ruslooob.Configuration;
import com.ruslooob.common.GeneticAlgorithmStatistics;
import com.ruslooob.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();
        //только в этом месте в приложении может меняться конфигурация. Во всех остальных она только читается
        var config = Configuration.INSTANCE;

        for (int i = 0; i < config.getNumberOfRuns(); i++) {
            log.info("\nRUN#{}", i + 1);
            var geneticAlgorithm = new GeneticAlgorithmSolver();
            geneticAlgorithm.solve();
            statistics.add(geneticAlgorithm.getStatistics());
        }

        log.info("=====Algo statistics=====");
        int averageGenerations = (int) calculateAverage(statistics, GeneticAlgorithmStatistics::generationNumberCount);
        double averageErrorRate = calculateAverage(statistics, GeneticAlgorithmStatistics::errorRate);
        log.info("Average number of generation to find optimum: {}", averageGenerations);
        log.info("Mean error rate: {}", averageErrorRate);
        new StatisticsExporter(config, "real_number")
                .export(averageGenerations, averageErrorRate);
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }
}