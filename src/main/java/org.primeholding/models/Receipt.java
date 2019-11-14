package org.primeholding.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Receipt {
    @XmlElement (name = "total")
    private double totalPrice;
    @XmlElement (name="datetime")
    private String dateTime;
    @XmlElement (name = "payment")
    private String paymentType;
    @XmlElement (name = "carddetails")
    private Card card;

    public Receipt() {
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }


    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
