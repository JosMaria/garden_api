package org.lievasoft.garden.entity;

public enum Classification {
    ALIMENTARY("alimentary"),
    CACTUS("cactus"),
    EXOTIC("exotic"),
    FOREST("forest"),
    FRUITFUL("fruitful"),
    GRASS("grass"),
    INDUSTRIAL("industrial"),
    MEDICINAL("medicinal"),
    ORNAMENTAL("ornamental"),
    SUCCULENT("succulent");

    private final String value;

    Classification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
