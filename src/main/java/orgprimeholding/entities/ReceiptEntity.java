package orgprimeholding.entities;

import orgprimeholding.annotations.Column;
import orgprimeholding.annotations.Id;

import java.time.LocalDateTime;

public class ReceiptEntity {
    @Id
    private int id;

    @Column
    private double total;

    @Column
    private LocalDateTime dateTime;

    @Column
    private String payment;

    private CardDetailsEntity card;

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
}
