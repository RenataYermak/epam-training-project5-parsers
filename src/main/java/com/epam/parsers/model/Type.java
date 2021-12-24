package com.epam.parsers.model;

public enum Type {
    CONGRATULATORY ("Congratulatory"),
    ADVERTISING ("Advertising"),
    USUAL ("Usual");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
