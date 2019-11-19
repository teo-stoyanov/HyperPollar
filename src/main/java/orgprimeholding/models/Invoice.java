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
    private double totalPrice;

    @XmlElement(name = "datetime",required = true)
    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDateTime dateTime;

    @XmlElement(name = "payment")
    private String paymentType;

    @XmlElement(name = "customer")
    private Customer customer;

    public Invoice() {
        /* JAXB need an non-arg constructor for unmarshalling */
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
