package com.ruslooob;

import com.ruslooob.real_number.common.DoubleInterval;

public class Configuration {
    // Размерность задачи
    public static final int DIMENSIONS = 2;
    // Количество особей в популяции
    public static final int INDIVIDUALS_IN_POPULATION_COUNT = 50;
    // Доля рекомбинаций в зависимости от от общего количества популяции, которая должна размножиться в одном поколении
    public static final double RECOMBINATION_RATE = 0.7;
    // Максимальмальное количество поколений
    public static final int MAX_GENERATIONS_COUNT = 500;
    // Порог конвергентности, нужен для определения схожести поколения и преждевременной остановки алгоритма
    public static final double CONVERGENT_THRESHOLD = 0.01;
    // Вероятность мутации
    public static final double MUTATION_RATE = 0.01;
    // Шаг мутации в алгоритмах с вещественынми генами
    public static final double PERTURBATION_RANGE = 0.1;
    // Интервал, на котором будет происходит поиск по X
    public static final DoubleInterval X_INTERVAL = new DoubleInterval(-100, 100);
    // Интервал, на котором будет происходит поиск по Y
    public static final DoubleInterval Y_INTERVAL = new DoubleInterval(-100, 100);
    // Константа d, используется в алгоритме промежуточной рекомбинации для вещественых генов
    public static final double INTERMEDIATE_D_CONSTANT = 0.25;
    // Доля усеченных особей от всей популяции. Используется в алгоритме селекции методом усечения.
    public static final double TRUNCATION_THRESHOLD = 0.5;
    // Количество прогонов одного алгоритма
    public static final int NUMBER_OF_RUNS = 50;
}
