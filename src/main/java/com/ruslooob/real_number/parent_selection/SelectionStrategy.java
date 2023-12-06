package com.ruslooob.real_number.parent_selection;

import com.ruslooob.real_number.model.Parents;

public interface SelectionStrategy {
    /**
     * Выбирает 2 родителей для скрещивания
     * */
    Parents selectParents();
}
