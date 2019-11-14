package org.primeholding.models;

import javax.xml.bind.annotation.XmlElement;

public class Card {
    @XmlElement (name = "cardtype")
    private String cardType;
    @XmlElement(name = "number")
    private String number;
    @XmlElement(name = "contactless")
    private boolean contactless;

    public Card() {
    }
}
