package orgprimeholding.core;

import orgprimeholding.entities.CardDetailsEntity;
import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.entities.CustomerEntity;
import orgprimeholding.entities.InvoiceEntity;
import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.CardRepository;
import orgprimeholding.repository.CompanyRepository;
import orgprimeholding.repository.CustomerRepository;
import orgprimeholding.repository.InvoiceRepository;
import orgprimeholding.repository.ReceiptRepository;
import orgprimeholding.repository.StoreRepository;

import java.sql.Connection;
import java.util.List;

public class EntitiesToDb {

    private EntitiesToDb() {
    }

    public static void insertToDb(List<CompanyEntity> companyEntities, Connection connection){
        for (CompanyEntity company : companyEntities) {
            CompanyRepository companyRepository = new CompanyRepository(company.getClass(), connection);
            int companyId = companyRepository.insert(company);
            for (StoreEntity store : company.getStores()) {
                StoreRepository storeRepository = new StoreRepository(store.getClass(), connection);
                int storeId = storeRepository.insert(store);
                storeRepository.setCompanyId(companyId, storeId);
                for (ReceiptEntity receipt : store.getReceipts()) {
                    ReceiptRepository receiptRepository = new ReceiptRepository(receipt.getClass(), connection);
                    int receiptId = receiptRepository.insert(receipt);
                    receiptRepository.setStoreId(storeId, receiptId);
                    CardDetailsEntity card = receipt.getCard();
                    if (card != null) {
                        CardRepository cardRepository = new CardRepository(card.getClass(), connection);
                        int cardId = cardRepository.insert(card);
                        receiptRepository.setCardId(cardId, receiptId);
                    }
                }
                for (InvoiceEntity invoice : store.getInvoices()) {
                    InvoiceRepository invoiceRepository = new InvoiceRepository(invoice.getClass(), connection);
                    int invoiceId = invoiceRepository.insert(invoice);
                    invoiceRepository.setStoreId(storeId, invoiceId);
                    CardDetailsEntity card = invoice.getCard();
                    if (card != null) {
                        CardRepository cardRepository = new CardRepository(card.getClass(), connection);
                        int cardId = cardRepository.insert(card);
                        invoiceRepository.setCardId(cardId, invoiceId);
                    }
                    CustomerEntity customer = invoice.getCustomer();
                    if (customer != null) {
                        CustomerRepository customerRepository = new CustomerRepository(customer.getClass(), connection);
                        int customerId = customerRepository.insert(customer);
                        invoiceRepository.setCustomerId(customerId, invoiceId);
                    }
                }
            }
        }
    }
}
