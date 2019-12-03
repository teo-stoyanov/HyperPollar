package orgprimeholding.entities;


import orgprimeholding.annotations.Column;
import orgprimeholding.annotations.Id;

public class CardDetailsEntity {
    @Id
    private int id;

    @Column
    private String cardType;

    @Column
    private String number;

    @Column
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
