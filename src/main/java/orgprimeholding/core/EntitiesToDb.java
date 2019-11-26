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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EntitiesToDb {
    private static final Logger LOGGER = Logger.getLogger(EntitiesToDb.class.getName());
    private static final String LOG_MSG = " stored in DB.";
    private static final String SPACE = " ";

    private static final String COMPANY = "company";
    private static final String STORE = "store";
    private static final String CARD = "card_details";
    private static final String CUSTOMER = "customer";
    private static final String COMPANY_ID = "company_id";
    private static final String STORE_ID = "store_id";
    private static final String CARD_ID = "card_id";
    private static final String CUSTOMER_ID = "customer_id";

    private EntitiesToDb() {
    }

    public static void insertToDb(List<CompanyEntity> companyEntities, Connection connection) throws SQLException {
        for (CompanyEntity company : companyEntities) {
            if (!checkIfExistWithUuid(COMPANY, company.getUuid(), connection)) {
                createCompany(company, connection);
            }
            int companyId = returnIdByUuid(COMPANY, company.getUuid(), connection, COMPANY_ID);
            LOGGER.log(Level.INFO, company.getName() + SPACE + COMPANY + LOG_MSG);
            for (StoreEntity store : company.getStores()) {
                if (!checkIfExistWithNameAndAddress(STORE, store.getName(), store.getAddress(), connection)) {
                    createStore(store, connection, companyId);
                }
                int storeId = returnIdByNameAndAddress(STORE, store.getName(), store.getAddress(), connection, STORE_ID);
                LOGGER.log(Level.INFO, store.getName() + SPACE + STORE + LOG_MSG);
                for (ReceiptEntity receipt : store.getReceipts()) {
                    int receiptId = createReceiptAndReturnId(receipt, connection, storeId);
                    if (receipt.getCard() != null) {
                        int cardId = checkForCardAndReturnId(connection, receipt.getCard());
                        updateReceiptWithCard(receipt, connection, cardId, receiptId);
                    }
                    LOGGER.log(Level.INFO, receipt.getClass().getName() + LOG_MSG);
                }
                for (InvoiceEntity invoice : store.getInvoices()) {
                    int invoiceId = createInvoiceAndReturnId(invoice, connection, storeId);
                    if (invoice.getCard() != null) {
                        int cardId = checkForCardAndReturnId(connection, invoice.getCard());
                        updateInvoiceWithCard(invoice, connection, cardId, invoiceId);
                    }
                    if (invoice.getCustomer() != null) {
                        if (!checkIfExistWithUuid(CUSTOMER, invoice.getCustomer().getUuid(), connection)) {
                            createCustomer(invoice.getCustomer(), connection);
                            LOGGER.log(Level.INFO, invoice.getCustomer().getName() + SPACE + CUSTOMER + LOG_MSG);
                        }
                        int customerId = returnIdByUuid(CUSTOMER, invoice.getCustomer().getUuid(), connection, CUSTOMER_ID);
                        updateInvoiceWithCustomer(invoice, connection, customerId, invoiceId);
                    }
                    LOGGER.log(Level.INFO, invoice.getClass().getName() + LOG_MSG);
                }
            }
        }
    }

    private static int checkForCardAndReturnId(Connection connection, CardDetailsEntity card) throws SQLException {
        int cardId;
        if (!checkIfExistWithCardNumber(CARD, card.getNumber(), connection)) {
            cardId = createCardAndReturnId(card, connection);
            LOGGER.log(Level.INFO, card.getClass().getName() + SPACE + CARD + LOG_MSG);
        } else {
            cardId = returnIdByNumber(CARD, card.getNumber(), connection, CARD_ID);
        }
        return cardId;
    }

    private static boolean checkIfExistWithUuid(String table, String uuid, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `uuid` = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static boolean checkIfExistWithNameAndAddress(String table, String name, String address, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `name` = ? AND `address` = ?;");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static boolean checkIfExistWithCardNumber(String table, String number, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `number` = ?");
        preparedStatement.setString(1, number);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    private static void createCompany(CompanyEntity company, Connection connection) {
        CompanyRepository companyRepository = new CompanyRepository(company.getClass(), connection);
        companyRepository.insert(company);
    }

    private static void createStore(StoreEntity store, Connection connection, Integer companyId) {
        StoreRepository storeRepository = new StoreRepository(store.getClass(), connection);
        storeRepository.insert(store);
        int storeId = storeRepository.getId();
        storeRepository.setCompanyId(companyId, storeId);
    }

    private static void createCustomer(CustomerEntity customer, Connection connection) {
        CustomerRepository customerRepository = new CustomerRepository(customer.getClass(), connection);
        customerRepository.insert(customer);
    }

    private static int createCardAndReturnId(CardDetailsEntity card, Connection connection) {
        CardRepository cardRepository = new CardRepository(card.getClass(), connection);
        cardRepository.insert(card);
        return cardRepository.getId();
    }

    private static int createReceiptAndReturnId(ReceiptEntity receipt, Connection connection, Integer storeId) {
        ReceiptRepository receiptRepository = new ReceiptRepository(receipt.getClass(), connection);
        receiptRepository.insert(receipt);
        int receiptId = receiptRepository.getId();
        receiptRepository.setStoreId(storeId, receiptId);
        return receiptId;
    }

    private static int createInvoiceAndReturnId(InvoiceEntity invoice, Connection connection, Integer storeId) {
        InvoiceRepository invoiceRepository = new InvoiceRepository(invoice.getClass(), connection);
        invoiceRepository.insert(invoice);
        int invoiceId = invoiceRepository.getId();
        invoiceRepository.setStoreId(storeId, invoiceId);
        return invoiceId;
    }

    private static void updateReceiptWithCard(ReceiptEntity receipt, Connection connection, Integer cardId, Integer receiptId) {
        ReceiptRepository receiptRepository = new ReceiptRepository(receipt.getClass(), connection);
        receiptRepository.setCardId(cardId, receiptId);
    }

    private static void updateInvoiceWithCard(InvoiceEntity invoice, Connection connection, Integer cardId, Integer invoiceId) {
        InvoiceRepository invoiceRepository = new InvoiceRepository(invoice.getClass(), connection);
        invoiceRepository.setCardId(cardId, invoiceId);
    }

    private static void updateInvoiceWithCustomer(InvoiceEntity invoice, Connection connection, Integer customerId, Integer invoiceId) {
        InvoiceRepository invoiceRepository = new InvoiceRepository(invoice.getClass(), connection);
        invoiceRepository.setCustomerId(customerId, invoiceId);
    }

    private static int returnIdByUuid(String table, String uuid, Connection connection, String tableId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `uuid` = ?");
        preparedStatement.setString(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(tableId);
        }
        return -1;
    }

    private static int returnIdByNameAndAddress(String table, String name, String address, Connection connection, String tableId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `name` = ? AND `address` = ?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            return resultSet.getInt(tableId);
        }
        return -1;
    }

    private static int returnIdByNumber(String table, String number, Connection connection, String tableId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM " + table + " WHERE `number` = ?");
        preparedStatement.setString(1, number);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(tableId);
        }
        return -1;
    }
}
