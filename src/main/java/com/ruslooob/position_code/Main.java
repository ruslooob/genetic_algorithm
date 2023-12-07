package com.ruslooob.position_code;

import com.ruslooob.Configuration;
import com.ruslooob.common.GeneticAlgorithmStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        var statistics = new ArrayList<GeneticAlgorithmStatistics>();

        for (int i = 0; i < Configuration.NUMBER_OF_RUNS; i++) {
            log.info("\nRUN#{}", i + 1);
            var geneticAlgorithm = new GeneticAlgorithmPerformer();
            geneticAlgorithm.start();
            statistics.add(geneticAlgorithm.getStatistics());
        }

        log.info("=====Algo statistics=====");
        log.info("Average number of generation to find optimum: {}", (int) calculateAverage(statistics, GeneticAlgorithmStatistics::generationNumberCount));
        log.info("Mean error rate: {}", calculateAverage(statistics, GeneticAlgorithmStatistics::errorRate));
    }

    private static double calculateAverage(List<GeneticAlgorithmStatistics> statistics, ToDoubleFunction<? super GeneticAlgorithmStatistics> statisticsParamGetter) {
        return statistics.stream()
                .mapToDouble(statisticsParamGetter)
                .average().orElseThrow();
    }
}