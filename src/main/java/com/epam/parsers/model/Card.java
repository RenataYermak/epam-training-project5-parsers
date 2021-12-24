package com.epam.parsers.model;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class Card {
    private Long id;
    private Date year;
    private String country;
    private String author;
    private Valuable valuable;
    private Theme theme;
    private Type type;

    public Card() {
    }

    public Card(Long id, Date year, String country, String author, Valuable valuable, Theme theme, Type type) {
        this.id = id;
        this.year = year;
        this.country = country;
        this.author = author;
        this.valuable = valuable;
        this.theme = theme;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        if (year != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(year);
            return calendar.get(Calendar.YEAR);
        }
        return null;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Valuable getValuable() {
        return valuable;
    }

    public void setValuable(Valuable valuable) {
        this.valuable = valuable;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id.equals(card.id) && Objects.equals(year, card.year) &&
                country.equals(card.country) && Objects.equals(author, card.author) &&
                valuable == card.valuable && theme == card.theme && type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, country, author, valuable, theme, type);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", year=" + year +
                ", Country='" + country + '\'' +
                ", author='" + author + '\'' +
                ", valuable=" + valuable +
                ", theme=" + theme +
                ", type=" + type +
                '}';
    }
}
