package com.ruslooob.common;

import com.ruslooob.position_code.model.Individ;

// замерять скорость работы алгоритма, количество затраченной памяти
public record GeneticAlgorithmStatistics(int generationNumberCount,
                                         double errorRate) {
}
