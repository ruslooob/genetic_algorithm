package com.ruslooob.real_number;

import com.ruslooob.Configuration;
import com.ruslooob.real_number.model.GeneticAlgorithmStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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

        int averageGenerationNumberPerRun =  (int) statistics.stream()
                .mapToDouble(GeneticAlgorithmStatistics::generationNumberCount)
                .average().orElseThrow();

        log.info("Average number of generation to find optimum: {}", averageGenerationNumberPerRun);
    }
}