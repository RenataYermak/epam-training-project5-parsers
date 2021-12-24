package com.epam.parsers.service.parser.dom;

import com.epam.parsers.model.Card;
import com.epam.parsers.model.Theme;
import com.epam.parsers.model.Type;
import com.epam.parsers.model.Valuable;

import com.epam.parsers.service.parser.AbstractCardBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * DOM Parser
 */
public class CardDomBuilder extends AbstractCardBuilder {
    private DocumentBuilder docBuilder;

    public CardDomBuilder() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            docBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buildSetCards(String xmlFile) {
        try {
            Document document = docBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("Card");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) list.item(i);
                    Card card = buildCard(element);
                    cards.add(card);
                }
            }
        } catch (SAXException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private Card buildCard(Element element) throws ParseException {
        Card card = new Card();
        card.setId(Long.parseLong(getTextContent(element, "Id")));
        card.setAuthor(getTextContent(element, "Author"));
        card.setCountry(getTextContent(element, "Country"));
        card.setYear(new SimpleDateFormat("yyyy").parse(getTextContent(element, "Year")));
        card.setTheme(Theme.valueOf(getTextContent(element, "Theme").toUpperCase()));
        card.setType(Type.valueOf(getTextContent(element, "Type").toUpperCase()));
        card.setValuable(Valuable.valueOf(getTextContent(element, "Valuable").toUpperCase()));
        return card;
    }

    private static String getTextContent(Element element, String elementName) {
        return element.getElementsByTagName(elementName).item(0).getTextContent();
    }
}
