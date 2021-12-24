package com.epam.parsers.model;

public enum Valuable {
    HISTORICAL("Historical"),
    COLLECTIBLE("Collectible"),
    THEMATIC("Thematic");

    private String name;

    Valuable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
