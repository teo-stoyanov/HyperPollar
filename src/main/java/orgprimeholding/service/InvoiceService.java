package orgprimeholding.service;

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

    InvoiceEntity getFromDb(Integer id){
        InvoiceEntity invoiceEntity = this.invoiceRepository.get(id);
        Integer cardId = this.invoiceRepository.getCardIdWithInvoiceId(invoiceEntity.getId());
        if(cardId != null){
            CardService cardService = new CardService(this.connection);
            invoiceEntity.setCard(cardService.getFromDb(cardId));
        }

        Integer customerId = this.invoiceRepository.getCustomerIdWithInvoiceId(invoiceEntity.getId());
        if(customerId != null){
            CustomerService customerService = new CustomerService(this.connection);
            invoiceEntity.setCustomer(customerService.getFromDb(customerId));
        }

        return invoiceEntity;
    }

    private void updateInvoiceWithCard(Integer cardId, Integer invoiceId) {
        this.invoiceRepository.setCardId(cardId, invoiceId);
    }

    private void updateInvoiceWithCustomer(Integer customerId, Integer invoiceId) {
        this.invoiceRepository.setCustomerId(customerId, invoiceId);
    }

}
