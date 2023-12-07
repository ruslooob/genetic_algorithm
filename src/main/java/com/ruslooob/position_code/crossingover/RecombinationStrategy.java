package com.ruslooob.position_code.crossingover;

import com.ruslooob.common.Pair;
import com.ruslooob.position_code.model.Individ;

public interface RecombinationStrategy {
    /**
     * Скрещивание родителей для получения пары дочерних особей. Некоторые алгоритмы могут изменять родительские особи.
     */
    Pair<Individ> recombine();
}
