package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class CustomerEntity {
    @Id
    private Integer id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    String uuid;

    public CustomerEntity() {
        /* We need empty constructor, because some fields might be null*/
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
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
