package com.epam.parsers.service.parser.sax;

import com.epam.parsers.model.Card;
import com.epam.parsers.model.Theme;
import com.epam.parsers.model.Type;
import com.epam.parsers.model.Valuable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class CardHandler extends DefaultHandler {
    private final Set<Card> cards = new HashSet<>();
    private Card currentCard;
    private CardXmlTag currentTag;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equals("Card")) {
            currentCard = new Card();
        }
        currentTag = CardXmlTag.valueOf(qName.toUpperCase());
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("Card")) {
            cards.add(currentCard);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        try {
            if (!data.isEmpty()) {
                switch (currentTag) {
                    case ID:
                        currentCard.setId(Long.parseLong(data.trim()));
                        break;
                    case AUTHOR:
                        currentCard.setAuthor(data.trim());
                        break;
                    case COUNTRY:
                        currentCard.setCountry(data.trim());
                        break;
                    case YEAR:
                        currentCard.setYear(new SimpleDateFormat("yyyy").parse(data.trim()));
                        break;
                    case THEME:
                        currentCard.setTheme(Theme.valueOf(data.toUpperCase().trim()));
                        break;
                    case TYPE:
                        currentCard.setType(Type.valueOf(data.toUpperCase().trim()));
                        break;
                    case VALUABLE:
                        currentCard.setValuable(Valuable.valueOf(data.toUpperCase().trim()));
                        break;
                    default:
                        throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Set<Card> getCards() {
        return cards;
    }
}
