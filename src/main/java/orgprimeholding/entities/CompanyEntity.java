package orgprimeholding.entities;

import orgprimeholding.annotations.Column;
import orgprimeholding.annotations.Id;

import java.util.ArrayList;
import java.util.List;

public class CompanyEntity {
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String uuid;

    private List<StoreEntity> stores;

    public CompanyEntity() {
        this.stores = new ArrayList<>();
        /* We need empty constructor, because some fields might be null*/
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
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

    public List<StoreEntity> getStores() {
        return this.stores;
    }

    public void setStores(List<StoreEntity> stores) {
        this.stores = stores;
    }
}
