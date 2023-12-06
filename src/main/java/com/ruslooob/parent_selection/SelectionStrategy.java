package com.ruslooob.parent_selection;

import com.ruslooob.model.Parents;

public interface SelectionStrategy {
    /**
     * Выбирает 2 родителей для скрещивания
     * */
    Parents selectParents();
}
