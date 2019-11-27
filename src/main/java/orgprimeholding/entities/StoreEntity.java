package orgprimeholding.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Table
public class StoreEntity{
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String address;

    private List<ReceiptEntity> receipts;

    private List<InvoiceEntity> invoices;

    private Integer companyId;

    public StoreEntity() {
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

    public List<ReceiptEntity> getReceipts() {
        return this.receipts;
    }

    public void setReceipts(List<ReceiptEntity> receipts) {
        this.receipts = receipts;
    }

    public List<InvoiceEntity> getInvoices() {
        return this.invoices;
    }

    public void setInvoices(List<InvoiceEntity> invoices) {
        this.invoices = invoices;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
