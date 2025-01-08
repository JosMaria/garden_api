package org.lievasoft.garden.entity;

public enum Situation {
    ABSENT("absent"),
    AVAILABLE("available"),
    PRESERVED("preserved");

    private final String value;

    Situation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
