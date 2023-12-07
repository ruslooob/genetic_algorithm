package com.ruslooob.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ruslooob.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class StatisticsExporter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Configuration config;
    private final String algoType;

    public StatisticsExporter(Configuration config, String algoType) {
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.config = config;
        this.algoType = algoType;
    }

    public void export(int averageGenerations, double errorRate) {
        try {
            String configString = objectMapper.writeValueAsString(config);
            String report = """
                    Конфигурация: %s
                    ____________
                    Среднее количество поколений: %s
                    Средняя величина ошибки: %s
                    """.formatted(configString, averageGenerations, errorRate);
            Files.write(Path.of(getPath()), report.getBytes(), StandardOpenOption.CREATE);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath() {
        return "%s%s%s-statistics#%s.txt".formatted("reports", File.separator, algoType, config.hashCode());
    }
}
