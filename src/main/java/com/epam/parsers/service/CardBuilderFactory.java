package com.epam.parsers.service;

import com.epam.parsers.service.parser.AbstractCardBuilder;
import com.epam.parsers.service.parser.dom.CardDomBuilder;
import com.epam.parsers.service.parser.sax.CardSaxBuilder;
import com.epam.parsers.service.parser.stax.CardStaxBuilder;

public class CardBuilderFactory {
    private enum TypeParser {
        SAX, STAX, DOM
    }

    private CardBuilderFactory() {
    }

    public static AbstractCardBuilder createCardBuilder(String typeParser) {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new CardDomBuilder();
            case SAX:
                return new CardSaxBuilder();
            case STAX:
                return new CardStaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
