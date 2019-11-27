package orgprimeholding.service.repository.service;

import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.repository.InvoiceRepository;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceService {
    private static final Logger LOGGER = Logger.getLogger(InvoiceService.class.getName());

    private Connection connection;
    private InvoiceRepository invoiceRepository;

    public InvoiceService(Connection connection) {
        this.connection = connection;
        this.invoiceRepository = new InvoiceRepository(InvoiceEntity.class, this.connection);
    }

    public Integer insertToDb(InvoiceEntity invoiceEntity, Integer storeId) {
        int invoiceId = this.invoiceRepository.insert(invoiceEntity);
        this.invoiceRepository.setStoreId(storeId, invoiceId);
        LOGGER.log(Level.INFO, "Invoice inserted.");
        return invoiceId;
    }

    public void updateInvoiceWithCard(Integer cardId, Integer invoiceId) {
        this.invoiceRepository.setCardId(cardId, invoiceId);
    }

    public void updateInvoiceWithCustomer(Integer customerId, Integer invoiceId) {
        this.invoiceRepository.setCustomerId(customerId, invoiceId);
    }

}
