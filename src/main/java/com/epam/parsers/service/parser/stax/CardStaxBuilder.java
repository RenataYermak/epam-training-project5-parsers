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

    @Override
    public void buildSetCards(String xmlFile) {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader;
        try (FileInputStream inputStream = new FileInputStream(xmlFile)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name;
                    name = reader.getLocalName();
                    if (name.equals(CardXmlTag.CARD.getName())) {
                        Card card = buildCard(reader);
                        cards.add(card);
                    }
                }
            }
        } catch (IOException | XMLStreamException | ParseException e) {
            throw new RuntimeException("Error in method buildSetCards" + e);
        }
    }

    private Card buildCard(XMLStreamReader reader) throws XMLStreamException, ParseException {
        Card card = new Card();
        card.setId(Long.valueOf(reader.getAttributeValue(null, CardXmlTag.ID.getName())));
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (CardXmlTag.valueOf(name.toUpperCase())) {
                        case ID:
                            card.setId(Long.valueOf(getXMLText(reader)));
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
                            card.setTheme(Theme.valueOf(getXMLText(reader)));
                            break;
                        case TYPE:
                            card.setType(Type.valueOf(getXMLText(reader)));
                            break;
                        case VALUABLE:
                            card.setValuable(Valuable.valueOf(getXMLText(reader)));
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
