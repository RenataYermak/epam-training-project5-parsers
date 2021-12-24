package com.epam.parsers.service.parser.stax;

import com.epam.parsers.model.Card;
import com.epam.parsers.model.Theme;
import com.epam.parsers.model.Type;
import com.epam.parsers.model.Valuable;
import com.epam.parsers.service.parser.AbstractCardBuilder;
import com.epam.parsers.service.parser.sax.CardXmlTag;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            e.printStackTrace();
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


    /*@Override
    public void buildSetCards(String xmlFile) {
        Card card = null;
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = inputFactory.createXMLEventReader(new FileInputStream(xmlFile));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals("card")) {
                        card = new Card();
                        Attribute id = startElement.getAttributeByName(new QName("Id"));
                        if (id != null) {
                            card.setId(Long.valueOf(id.getValue()));
                        }
                    }
                    else if (startElement.getName().getLocalPart().equals("Theme")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setTheme(Theme.valueOf(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Type")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setType(Type.valueOf(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Country")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setCountry(event.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("Year")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setYear(new SimpleDateFormat("yyyy").parse(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("Author")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setAuthor(event.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("Valuable")) {
                        event = reader.nextEvent();
                        assert card != null;
                        card.setValuable(Valuable.valueOf(event.asCharacters().getData()));
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Card")) {
                        cards.add(card);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException | ParseException e) {
            e.printStackTrace();
        }
    }*/



