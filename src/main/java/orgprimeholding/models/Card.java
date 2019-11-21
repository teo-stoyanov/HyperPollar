package orgprimeholding.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    @XmlElement (name = "cardtype")
    private String cardType;

    @XmlElement(name = "number")
    private String number;

    @XmlElement(name = "contactless")
    private boolean contactless;

    public Card() {
        /* JAXB need an non-arg constructor for unmarshalling */
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isContactless() {
        return this.contactless;
    }

    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }
}
