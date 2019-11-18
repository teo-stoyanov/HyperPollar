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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
