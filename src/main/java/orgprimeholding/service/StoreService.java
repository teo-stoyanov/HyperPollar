package orgprimeholding.service;

import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.StoreRepository;

import java.sql.Connection;
import java.util.List;

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

    StoreEntity getFromDb(Integer id){
        StoreEntity storeEntity = this.storeRepository.get(id);
        List<Integer> receiptIds = this.storeRepository.getReceiptIds(storeEntity.getId());
        ReceiptService receiptService = new ReceiptService(this.connection);
        for (Integer receiptId : receiptIds) {
            storeEntity.getReceipts().add(receiptService.getFromDb(receiptId));
        }

        List<Integer> invoiceIds = this.storeRepository.getInvoiceIds(storeEntity.getId());
        InvoiceService invoiceService = new InvoiceService(this.connection);
        for (Integer invoiceId : invoiceIds) {
            storeEntity.getInvoices().add(invoiceService.getFromDb(invoiceId));
        }

        return  storeEntity;
    }
}
