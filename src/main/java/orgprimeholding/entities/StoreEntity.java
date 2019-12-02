package orgprimeholding.entities;

import orgprimeholding.annotations.Column;
import orgprimeholding.annotations.Id;

import java.util.ArrayList;
import java.util.List;

public class StoreEntity {
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String address;

    private List<ReceiptEntity> receipts;

    private List<InvoiceEntity> invoices;

    public StoreEntity() {
        this.receipts = new ArrayList<>();
        this.invoices = new ArrayList<>();
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
}