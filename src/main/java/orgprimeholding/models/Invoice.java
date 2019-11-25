package orgprimeholding.models;

import orgprimeholding.service.parsers.DateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice {
    @XmlElement(name = "total")
    private double total;

    @XmlElement(name = "datetime",required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime dateTime;

    @XmlElement(name = "payment")
    private String payment;

    @XmlElement(name = "customer")
    private Customer customer;

    @XmlElement (name = "carddetails")
    private Card card;

    public Invoice() {
        /* JAXB need an non-arg constructor for unmarshalling */
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

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
