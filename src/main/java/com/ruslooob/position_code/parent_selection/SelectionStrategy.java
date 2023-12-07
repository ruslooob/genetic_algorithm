package com.ruslooob.position_code.parent_selection;


import com.ruslooob.position_code.model.Parents;

public interface SelectionStrategy {
    /**
     * Выбирает 2 родителей для скрещивания
     * */
    Parents selectParents();
}
