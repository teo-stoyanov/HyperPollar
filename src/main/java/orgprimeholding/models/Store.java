package orgprimeholding.models;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Store {
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String address;

    @XmlElementWrapper(name="receipts")
    @XmlElement (name = "receipt")
    private List<Receipt> receipts;

    @XmlElementWrapper(name="invoices")
    @XmlElement (name = "invoice")
    private List<Invoice> invoices;

    public Store() {
        /* JAXB need an non-arg constructor for unmarshalling */
    }

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
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
}
