package orgprimeholding.service.repository.service;

import orgprimeholding.entities.ReceiptEntity;
import orgprimeholding.repository.ReceiptRepository;

import java.sql.Connection;

public class ReceiptService {
    private Connection connection;
    private ReceiptRepository receiptRepository;

    ReceiptService(Connection connection) {
        this.connection = connection;
        this.receiptRepository = new ReceiptRepository(ReceiptEntity.class, this.connection);
    }

    void insertToDb(ReceiptEntity receipt, Integer storeId) {
        int receiptId = this.receiptRepository.insert(receipt);
        this.receiptRepository.setStoreId(storeId, receiptId);

        if (receipt.getCard() != null) {
            CardService cardService = new CardService(this.connection);
            cardService.insertToDb(receipt.getCard());
            this.updateReceiptWithCard(cardService.getCardId(), receiptId);
        }
    }

    private void updateReceiptWithCard(Integer cardId, Integer receiptId) {
        this.receiptRepository.setCardId(cardId, receiptId);
    }
}
