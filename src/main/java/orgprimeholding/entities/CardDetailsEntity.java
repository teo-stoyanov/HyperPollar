package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Table;

@Table
public class CardDetailsEntity {
    @Column(name = "cardtype")
    private String cardType;

    @Column(name = "number")
    private String number;

    @Column(name = "contactless")
    private boolean contactless;

    public CardDetailsEntity() {
        /* We need empty constructor, because some fields might be null*/
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
