package com.epam.parsers.service.parser;

import com.epam.parsers.model.Card;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCardBuilder {
    protected Set<Card> cards;

    public AbstractCardBuilder() {
        cards = new HashSet<>();
    }

    public Set<Card> getCards() {
        return cards;
    }

    public abstract void buildSetCards(String xmlFile);
}
