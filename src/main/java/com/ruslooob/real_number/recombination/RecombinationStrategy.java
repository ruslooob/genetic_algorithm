package com.ruslooob.real_number.recombination;

import com.ruslooob.real_number.common.Pair;
import com.ruslooob.real_number.model.Individ;

public interface RecombinationStrategy {
    /**
     * Скрещивание родителей для получения пары дочерних особей. Некоторые алгоритмы могут изменять родительские особи.
     */
    Pair<Individ> recombine();
}
