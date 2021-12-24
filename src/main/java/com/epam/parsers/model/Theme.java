package com.epam.parsers.model;

public enum Theme {
    CITYSCAPE ("Cityscape"),
    NATURE ("Nature"),
    PEOPLE ("People"),
    RELIGION ("Religion"),
    SPORT ("Sport"),
    ARCHITECTURE ("Architecture");

    private String name;

    Theme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
