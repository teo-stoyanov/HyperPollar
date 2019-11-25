package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table
public class InvoiceEntity {
    @Column (name = "total")
    private double total;

    @Column (name = "datetime")
    private LocalDateTime dateTime;

    @Column(name = "payment")
    private String payment;

    private CustomerEntity customer;

    private CardDetailsEntity card;

    public InvoiceEntity() {
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

    public CustomerEntity getCustomer() {
        return this.customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CardDetailsEntity getCard() {
        return this.card;
    }

    public void setCard(CardDetailsEntity card) {
        this.card = card;
    }
}
