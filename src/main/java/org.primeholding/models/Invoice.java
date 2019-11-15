package org.primeholding.models;

import org.primeholding.service.DateAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

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
    }
}
