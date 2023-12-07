package com.ruslooob.position_code.natural_selection;

public interface NaturalSelectionStrategy {
    /**
     * Отфильтровывает популцию в соответсвии от наименее приспособленных особей
     * */
    void filterGenerationPool();
}
