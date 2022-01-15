package com.epam.parsers.service.parser.sax;

import com.epam.parsers.service.parser.AbstractCardBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * SAX Parser
 */
public class CardSaxBuilder extends AbstractCardBuilder {

    private final CardHandler cardHandler = new CardHandler();
    private XMLReader reader;

    public CardSaxBuilder() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            reader = parser.getXMLReader();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException("Error in method CardSaxBuilder " + e);
        }
        reader.setContentHandler(cardHandler);
    }

    @Override
    public void buildSetCards(String xmlFile) {
        try {
            reader.parse(xmlFile);
        } catch (IOException | SAXException e) {
            throw new RuntimeException("Error in method buildSetCards " + e);
        }
        cards = cardHandler.getCards();
    }
}
