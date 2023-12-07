package com.ruslooob.position_code.model;

public class Parents {
    private final Individ firstParent;
    private final Individ secondParent;

    public Parents(Individ firstParent, Individ secondParent) {
        this.firstParent = firstParent;
        this.secondParent = secondParent;
    }

    public Individ getFirstParent() {
        return firstParent;
    }

    public Individ getSecondParent() {
        return secondParent;
    }
}
