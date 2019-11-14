package org.primeholding.models;

import javax.xml.bind.annotation.XmlElement;

public class Customer {
    @XmlElement (name = "name")
    private String name;
    @XmlElement(name = "address")
    private String address;
    @XmlElement (name = "uuid")
    private String uuid;

    public Customer() {
    }
}
