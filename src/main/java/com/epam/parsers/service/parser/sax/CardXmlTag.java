package com.epam.parsers.service.parser.sax;

public enum CardXmlTag {
    OLDCARDS("OldCards"),
    CARD("Card"),
    ID("Id"),
    YEAR("Year"),
    COUNTRY("Country"),
    AUTHOR("Author"),
    VALUABLE("Valuable"),
    THEME("Theme"),
    TYPE("Type");

    private String name;

    CardXmlTag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
