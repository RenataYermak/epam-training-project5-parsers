package com.epam.parsers.service.parser.stax;

import com.epam.parsers.model.Card;
import com.epam.parsers.model.Theme;
import com.epam.parsers.model.Type;
import com.epam.parsers.model.Valuable;
import com.epam.parsers.service.parser.AbstractCardBuilder;
import com.epam.parsers.service.parser.sax.CardXmlTag;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * StAX Parser
 */
public class CardStaxBuilder extends AbstractCardBuilder {

    private final XMLInputFactory inputFactory;

    public CardStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetCards(String xmlFile) {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(xmlFile)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(CardXmlTag.CARD.getName())) {
                        Card card = buildCard(reader);
                        cards.add(card);
                    }
                }
            }
        } catch (IOException | XMLStreamException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Card buildCard(XMLStreamReader reader) throws XMLStreamException, ParseException {
        Card card = new Card();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (CardXmlTag.valueOf(name.toUpperCase())) {
                        case ID:
                            card.setId(Long.valueOf(getXMLText(reader)));
                            break;
                        case AUTHOR:
                            card.setAuthor(getXMLText(reader));
                            break;
                        case COUNTRY:
                            card.setCountry(getXMLText(reader));
                            break;
                        case YEAR:
                            card.setYear(new SimpleDateFormat("yyyy").parse(getXMLText(reader)));
                            break;
                        case THEME:
                            card.setTheme(Theme.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case TYPE:
                            card.setType(Type.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                        case VALUABLE:
                            card.setValuable(Valuable.valueOf(getXMLText(reader).toUpperCase()));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (CardXmlTag.valueOf(name.toUpperCase()) == CardXmlTag.CARD) {
                        return card;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag <card>");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}