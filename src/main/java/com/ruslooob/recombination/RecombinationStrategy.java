package com.ruslooob.recombination;

import com.ruslooob.model.Individ;
import com.ruslooob.common.Pair;

public interface RecombinationStrategy {
    /**
     * Скрещивание родителей для получения пары дочерних особей. Некоторые алгоритмы могут изменять родительские особи.
     */
    Pair<Individ> recombine();
}
