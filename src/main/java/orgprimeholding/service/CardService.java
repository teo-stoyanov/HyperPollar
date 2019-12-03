package orgprimeholding.service;

import orgprimeholding.entities.CardDetailsEntity;
import orgprimeholding.repository.CardRepository;

import java.sql.Connection;

public class CardService {
    private Connection connection;
    private CardRepository cardRepository;
    private Integer cardId;

    CardService(Connection connection) {
        this.connection = connection;
        this.cardRepository = new CardRepository(CardDetailsEntity.class, this.connection);
    }

    void insertToDb(CardDetailsEntity cardDetailsEntity) {
        this.cardId = this.cardRepository.insert(cardDetailsEntity);
    }

    Integer getCardId() {
        return this.cardId;
    }

    CardDetailsEntity getFromDb(Integer id){
        return this.cardRepository.get(id);
    }
}
