package orgprimeholding.core;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.service.repository.service.CardService;
import orgprimeholding.service.repository.service.CompanyService;
import orgprimeholding.service.repository.service.CustomerService;
import orgprimeholding.service.repository.service.InvoiceService;
import orgprimeholding.service.repository.service.ReceiptService;
import orgprimeholding.service.repository.service.StoreService;

import java.sql.Connection;
import java.util.List;

public class EntitiesToDb {
    private List<CompanyEntity> companyEntities;
    private Connection connection;

    public EntitiesToDb(List<CompanyEntity> companyEntities, Connection connection) {
        this.companyEntities = companyEntities;
        this.connection = connection;
    }

    public void insertToDb() {
        for (CompanyEntity company : companyEntities) {
            CompanyService companyService = new CompanyService(this.connection);
            Integer companyId = companyService.insertIfNotExist(company);
            for (StoreEntity store : company.getStores()) {
                StoreService storeService = new StoreService(this.connection);
                Integer storeId = storeService.insertIfNotExist(store, companyId);
                for (ReceiptEntity receipt : store.getReceipts()) {
                    ReceiptService receiptService = new ReceiptService(this.connection);
                    Integer receiptId = receiptService.insertToDb(receipt, storeId);
                    if (receipt.getCard() != null) {
                        CardService cardService = new CardService(this.connection);
                        Integer cardId = cardService.insertIfNotExist(receipt.getCard());
                        receiptService.updateReceiptWithCard(cardId, receiptId);
                    }
                }
                for (InvoiceEntity invoice : store.getInvoices()) {
                    InvoiceService invoiceService = new InvoiceService(this.connection);
                    Integer invoiceId = invoiceService.insertToDb(invoice, storeId);
                    if (invoice.getCard() != null) {
                        CardService cardService = new CardService(this.connection);
                        Integer cardId = cardService.insertIfNotExist(invoice.getCard());
                        invoiceService.updateInvoiceWithCard(cardId, invoiceId);
                    }
                    if (invoice.getCustomer() != null) {
                        CustomerService customerService = new CustomerService(this.connection);
                        Integer customerId = customerService.insertIfNotExist(invoice.getCustomer());
                        invoiceService.updateInvoiceWithCustomer(customerId, invoiceId);
                    }
                }
            }
        }
    }
}
