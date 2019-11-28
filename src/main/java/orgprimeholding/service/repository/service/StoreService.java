package orgprimeholding.service.repository.service;

import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.StoreRepository;

import java.sql.Connection;

public class StoreService {
    private StoreRepository storeRepository;
    private Connection connection;

    StoreService(Connection connection) {
        this.connection = connection;
        this.storeRepository = new StoreRepository(StoreEntity.class, connection);
    }


    void insertToDb(StoreEntity storeEntity, Integer companyId) {
        Integer storeId = this.storeRepository.insert(storeEntity);
        this.storeRepository.setCompanyId(companyId, storeId);

        ReceiptService receiptService = new ReceiptService(this.connection);
        for (ReceiptEntity receiptEntity : storeEntity.getReceipts()) {
            receiptService.insertToDb(receiptEntity, storeId);
        }

        InvoiceService invoiceService = new InvoiceService(this.connection);
        for (InvoiceEntity invoice : storeEntity.getInvoices()) {
            invoiceService.insertToDb(invoice, storeId);
        }
    }
}
