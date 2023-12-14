package com.ruslooob;

import com.ruslooob.common.DoubleInterval;
import com.ruslooob.common.NaturalSelectionStrategyType;
import com.ruslooob.common.ParentSelectionStrategyType;

import java.util.Map;
import java.util.Objects;

//todo
// Тюнинг параметров, нахождение наилучших результатов из параметров
// Переписать api, чтобы можно было нормально пользоваться и внедрять зависимости
// Попробовать переписать алгоритм с бинарной мутацией, с более широким интервалом
// Прикрутить spring boot для более удобной работы с конфигурацией и DI
public class Configuration {
    public static Configuration INSTANCE = new Configuration();
    // Размерность задачи
    private int dimensions = 2;
    // Количество особей в популяции
    private int individualsInPopulationCount = 50;
    // Доля рекомбинаций в зависимости от от общего количества популяции, которая должна размножиться в одном поколении
    private double recombinationRate = 0.7;
    // Максимальмальное количество поколений
    private int maxGenerationsCount = 200;
    // Порог конвергентности, нужен для определения схожести поколения и преждевременной остановки алгоритма
    private double convergentThreshold = 0.000_001;
    // Вероятность мутации
    private double mutationRate = 0.01;
    // Шаг мутации в алгоритмах с вещественынми генами
    private double perturbationRange = 0.1;
    // Интервал, на котором будет происходит поиск по X
    private DoubleInterval xInterval = new DoubleInterval(-5, 10);
    // Интервал, на котором будет происходит поиск по Y
    private DoubleInterval yInterval = new DoubleInterval(0, 15);
    // Точность, которая достигается делением интврала на части. Используется при кодировании чисел с плавающей точкой
    private double precision = 0.000_001;
    // Константа d, используется в алгоритме промежуточной рекомбинации для вещественых генов
    private double intermediateDConstant = 0.25;
    // Доля усеченных особей от всей популяции. Используется в алгоритме селекции методом усечения.
    private double truncationThreshold = 0.5;
    // Количество прогонов одного алгоритма
    private int numberOfRuns = 50;
    // величина ошибки, которая будет считаться за неудачный запуск алгоритма
    private double errorThreshold = 0.5;
    private ParentSelectionStrategyType parentsSelectionStrategy = ParentSelectionStrategyType.ROULETTE_WHEEL;
    private NaturalSelectionStrategyType naturalSelectionStrategy = NaturalSelectionStrategyType.TRUNCATION;

    public Configuration() {
    }

    public static Configuration getConfig() {
        return INSTANCE;
    }

    public int getDimensions() {
        return INSTANCE.dimensions;
    }

    public Configuration setDimensions(int dimensions) {
        INSTANCE.dimensions = dimensions;
        return INSTANCE;
    }

    public int getIndividualsInPopulationCount() {
        return INSTANCE.individualsInPopulationCount;
    }

    public Configuration setIndividualsInPopulationCount(int individualsInPopulationCount) {
        INSTANCE.individualsInPopulationCount = individualsInPopulationCount;
        return INSTANCE;
    }

    public double getRecombinationRate() {
        return recombinationRate;
    }

    public Configuration setRecombinationRate(double recombinationRate) {
        INSTANCE.recombinationRate = recombinationRate;
        return INSTANCE;
    }

    public int getMaxGenerationsCount() {
        return maxGenerationsCount;
    }

    public Configuration setMaxGenerationsCount(int maxGenerationsCount) {
        INSTANCE.maxGenerationsCount = maxGenerationsCount;
        return INSTANCE;
    }

    public double getConvergentThreshold() {
        return convergentThreshold;
    }

    public Configuration setConvergentThreshold(double convergentThreshold) {
        INSTANCE.convergentThreshold = convergentThreshold;
        return INSTANCE;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public Configuration setMutationRate(double mutationRate) {
        INSTANCE.mutationRate = mutationRate;
        return INSTANCE;
    }

    public double getPerturbationRange() {
        return perturbationRange;
    }

    public Configuration setPerturbationRange(double perturbationRange) {
        INSTANCE.perturbationRange = perturbationRange;
        return INSTANCE;
    }

    public DoubleInterval getxInterval() {
        return xInterval;
    }

    public Configuration setxInterval(DoubleInterval xInterval) {
        INSTANCE.xInterval = xInterval;
        return INSTANCE;
    }

    public DoubleInterval getyInterval() {
        return yInterval;
    }

    public Configuration setyInterval(DoubleInterval yInterval) {
        INSTANCE.yInterval = yInterval;
        return INSTANCE;
    }

    public double getPrecision() {
        return precision;
    }

    public Configuration setPrecision(double precision) {
        INSTANCE.precision = precision;
        return INSTANCE;
    }

    public double getIntermediateDConstant() {
        return intermediateDConstant;
    }

    public Configuration setIntermediateDConstant(double intermediateDConstant) {
        INSTANCE.intermediateDConstant = intermediateDConstant;
        return INSTANCE;
    }

    public double getTruncationThreshold() {
        return truncationThreshold;
    }

    public Configuration setTruncationThreshold(double truncationThreshold) {
        INSTANCE.truncationThreshold = truncationThreshold;
        return INSTANCE;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public Configuration setNumberOfRuns(int numberOfRuns) {
        INSTANCE.numberOfRuns = numberOfRuns;
        return INSTANCE;
    }

    public double getErrorThreshold() {
        return errorThreshold;
    }

    public Configuration setErrorThreshold(double errorThreshold) {
        INSTANCE.errorThreshold = errorThreshold;
        return INSTANCE;
    }

    public ParentSelectionStrategyType getParentsSelectionStrategy() {
        return parentsSelectionStrategy;
    }

    public Configuration setParentsSelectionStrategy(ParentSelectionStrategyType parentsSelectionStrategy) {
        INSTANCE.parentsSelectionStrategy = parentsSelectionStrategy;
        return INSTANCE;
    }

    public NaturalSelectionStrategyType getNaturalSelectionStrategy() {
        return naturalSelectionStrategy;
    }

    public Configuration setNaturalSelectionStrategy(NaturalSelectionStrategyType naturalSelectionStrategy) {
        INSTANCE.naturalSelectionStrategy = naturalSelectionStrategy;
        return INSTANCE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return dimensions == that.dimensions
                && individualsInPopulationCount == that.individualsInPopulationCount
                && Double.compare(recombinationRate, that.recombinationRate) == 0
                && maxGenerationsCount == that.maxGenerationsCount
                && Double.compare(convergentThreshold, that.convergentThreshold) == 0
                && Double.compare(mutationRate, that.mutationRate) == 0
                && Double.compare(perturbationRange, that.perturbationRange) == 0
                && Double.compare(precision, that.precision) == 0
                && Double.compare(intermediateDConstant, that.intermediateDConstant) == 0
                && Double.compare(truncationThreshold, that.truncationThreshold) == 0
                && numberOfRuns == that.numberOfRuns && Objects.equals(xInterval, that.xInterval)
                && Objects.equals(yInterval, that.yInterval)
                && Double.compare(errorThreshold, that.errorThreshold) == 0
                && parentsSelectionStrategy.compareTo(that.parentsSelectionStrategy) == 0
                && naturalSelectionStrategy.compareTo(that.naturalSelectionStrategy) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimensions, individualsInPopulationCount, recombinationRate, maxGenerationsCount,
                convergentThreshold, mutationRate, perturbationRange, xInterval, yInterval, precision, intermediateDConstant,
                truncationThreshold, numberOfRuns, errorThreshold, parentsSelectionStrategy, naturalSelectionStrategy);
    }
}
