package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class CardDetailsEntity {
    @Id
    private int id;

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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
