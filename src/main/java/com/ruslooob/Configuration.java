package com.ruslooob;

import com.ruslooob.common.DoubleInterval;

import java.util.Objects;

public class Configuration {
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
    private DoubleInterval xInterval = new DoubleInterval(-10, 10);
    // Интервал, на котором будет происходит поиск по Y
    private DoubleInterval yInterval = new DoubleInterval(-10, 10);
    // Точность, которая достигается делением интврала на части. Используется при кодировании чисел с плавающей точкой
    private double precision = 0.000_001;
    // Константа d, используется в алгоритме промежуточной рекомбинации для вещественых генов
    private double intermediateDConstant = 0.25;
    // Доля усеченных особей от всей популяции. Используется в алгоритме селекции методом усечения.
    private double truncationThreshold = 0.5;
    // Количество прогонов одного алгоритма
    private int numberOfRuns = 50;

    private Configuration() {
    }

    public static final Configuration INSTANCE = new Configuration();

    public static Configuration getConfig() {
       return INSTANCE;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public int getIndividualsInPopulationCount() {
        return individualsInPopulationCount;
    }

    public void setIndividualsInPopulationCount(int individualsInPopulationCount) {
        this.individualsInPopulationCount = individualsInPopulationCount;
    }

    public double getRecombinationRate() {
        return recombinationRate;
    }

    public void setRecombinationRate(double recombinationRate) {
        this.recombinationRate = recombinationRate;
    }

    public int getMaxGenerationsCount() {
        return maxGenerationsCount;
    }

    public void setMaxGenerationsCount(int maxGenerationsCount) {
        this.maxGenerationsCount = maxGenerationsCount;
    }

    public double getConvergentThreshold() {
        return convergentThreshold;
    }

    public void setConvergentThreshold(double convergentThreshold) {
        this.convergentThreshold = convergentThreshold;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public double getPerturbationRange() {
        return perturbationRange;
    }

    public void setPerturbationRange(double perturbationRange) {
        this.perturbationRange = perturbationRange;
    }

    public DoubleInterval getxInterval() {
        return xInterval;
    }

    public void setxInterval(DoubleInterval xInterval) {
        this.xInterval = xInterval;
    }

    public DoubleInterval getyInterval() {
        return yInterval;
    }

    public void setyInterval(DoubleInterval yInterval) {
        this.yInterval = yInterval;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getIntermediateDConstant() {
        return intermediateDConstant;
    }

    public void setIntermediateDConstant(double intermediateDConstant) {
        this.intermediateDConstant = intermediateDConstant;
    }

    public double getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(double truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    public int getNumberOfRuns() {
        return numberOfRuns;
    }

    public void setNumberOfRuns(int numberOfRuns) {
        this.numberOfRuns = numberOfRuns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return dimensions == that.dimensions && individualsInPopulationCount == that.individualsInPopulationCount && Double.compare(recombinationRate, that.recombinationRate) == 0 && maxGenerationsCount == that.maxGenerationsCount && Double.compare(convergentThreshold, that.convergentThreshold) == 0 && Double.compare(mutationRate, that.mutationRate) == 0 && Double.compare(perturbationRange, that.perturbationRange) == 0 && Double.compare(precision, that.precision) == 0 && Double.compare(intermediateDConstant, that.intermediateDConstant) == 0 && Double.compare(truncationThreshold, that.truncationThreshold) == 0 && numberOfRuns == that.numberOfRuns && Objects.equals(xInterval, that.xInterval) && Objects.equals(yInterval, that.yInterval);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dimensions, individualsInPopulationCount, recombinationRate, maxGenerationsCount, convergentThreshold, mutationRate, perturbationRange, xInterval, yInterval, precision, intermediateDConstant, truncationThreshold, numberOfRuns);
    }
}
