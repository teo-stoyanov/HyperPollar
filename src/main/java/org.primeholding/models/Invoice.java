package org.primeholding.models;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

public class Invoice {
    @XmlElement(name = "total")
    private double totalPrice;
    @XmlElement(name = "datetime")
    private String dateTime;
    @XmlElement(name = "payment")
    private String paymentType;
    @XmlElement(name = "customer")
    private Customer customer;

    public Invoice() {
    }
}
