package orgprimeholding.service.repository.service;

import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.repository.ReceiptRepository;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptService {
    private static final Logger LOGGER = Logger.getLogger(ReceiptService.class.getName());

    private Connection connection;
    private ReceiptRepository receiptRepository;

    public ReceiptService(Connection connection) {
        this.connection = connection;
        this.receiptRepository = new ReceiptRepository(ReceiptEntity.class, this.connection);
    }

    public Integer insertToDb(ReceiptEntity receipt, Integer storeId) {
        int receiptId = this.receiptRepository.insert(receipt);
        this.receiptRepository.setStoreId(storeId, receiptId);
        LOGGER.log(Level.INFO, "Receipt inserted.");
        return receiptId;
    }

    public void updateReceiptWithCard(Integer cardId, Integer receiptId) {
        this.receiptRepository.setCardId(cardId, receiptId);
    }
}
