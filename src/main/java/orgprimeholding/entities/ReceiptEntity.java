package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class ReceiptEntity {
    @Id
    private int id;

    @Column(name = "total")
    private double total;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @Column(name = "payment")
    private String payment;

    private CardDetailsEntity card;

    private Integer cardId;

    private Integer storeId;

    public ReceiptEntity() {
        /* We need empty constructor, because some fields might be null*/
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPayment() {
        return this.payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public CardDetailsEntity getCard() {
        return this.card;
    }

    public void setCard(CardDetailsEntity card) {
        this.card = card;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCardId() {
        return this.cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getStoreId() {
        return this.storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
