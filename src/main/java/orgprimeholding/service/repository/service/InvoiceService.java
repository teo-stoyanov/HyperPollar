package orgprimeholding.service.repository.service;

import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.repository.InvoiceRepository;

import java.sql.Connection;

public class InvoiceService {
    private Connection connection;
    private InvoiceRepository invoiceRepository;

    InvoiceService(Connection connection) {
        this.connection = connection;
        this.invoiceRepository = new InvoiceRepository(InvoiceEntity.class, this.connection);
    }

    void insertToDb(InvoiceEntity invoiceEntity, Integer storeId) {
        int invoiceId = this.invoiceRepository.insert(invoiceEntity);
        this.invoiceRepository.setStoreId(storeId, invoiceId);

        if (invoiceEntity.getCard() != null) {
            CardService cardService = new CardService(this.connection);
            cardService.insertToDb(invoiceEntity.getCard());
            this.updateInvoiceWithCard(cardService.getCardId(), invoiceId);
        }

        if (invoiceEntity.getCustomer() != null) {
            CustomerService customerService = new CustomerService(this.connection);
            customerService.insertToDb(invoiceEntity.getCustomer());
            this.updateInvoiceWithCustomer(customerService.getCustomerId(), invoiceId);
        }
    }

    private void updateInvoiceWithCard(Integer cardId, Integer invoiceId) {
        this.invoiceRepository.setCardId(cardId, invoiceId);
    }

    private void updateInvoiceWithCustomer(Integer customerId, Integer invoiceId) {
        this.invoiceRepository.setCustomerId(customerId, invoiceId);
    }

}
