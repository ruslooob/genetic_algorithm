package com.ruslooob.position_code;

import com.ruslooob.common.GeneticAlgorithmStatistics;
import com.ruslooob.report.StatisticsExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static com.ruslooob.Configuration.getConfig;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();
        var config = getConfig();

        for (int i = 0; i < getConfig().getNumberOfRuns(); i++) {
            log.info("\nRUN#{}", i + 1);
            var geneticAlgorithm = new GeneticAlgorithmPerformer();
            geneticAlgorithm.start();
            statistics.add(geneticAlgorithm.getStatistics());
        }

        log.info("=====Algo statistics=====");
        int averageGenerations = (int) calculateAverage(statistics, GeneticAlgorithmStatistics::generationNumberCount);
        log.info("Average number of generation to find optimum: {}", averageGenerations);
        double averageErrorRate = calculateAverage(statistics, GeneticAlgorithmStatistics::errorRate);
        log.info("Mean error rate: {}", averageErrorRate);
        new StatisticsExporter(config, "position_code")
                .export(averageGenerations, averageErrorRate);
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }
}