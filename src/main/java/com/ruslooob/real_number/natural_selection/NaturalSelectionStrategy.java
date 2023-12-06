package com.ruslooob.real_number.natural_selection;

public interface NaturalSelectionStrategy {
    /**
     * Отфильтровывает популцию в соответсвии от наименее приспособленных особей
     * */
    void filterGenerationPool();
}
