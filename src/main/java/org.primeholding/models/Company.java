package org.primeholding.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

    @XmlAttribute
    private String name;

    @XmlAttribute
    private String address;

    @XmlAttribute
    private String uuid;

    @XmlElementWrapper(name="stores")
    @XmlElement(name="store")
    private List<Store> stores;

    public Company() {
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

    public List<Store> getStores() {
        return this.stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
