package orgprimeholding.service.repository.service;

import orgprimeholding.entities.CardDetailsEntity;
import orgprimeholding.repository.CardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardService {
    private static final Logger LOGGER = Logger.getLogger(CardService.class.getName());
    private static final String LOG_MSG = " card is stored in DB.";

    private Connection connection;
    private CardRepository cardRepository;

    public CardService(Connection connection) {
        this.connection = connection;
        this.cardRepository = new CardRepository(CardDetailsEntity.class, connection);
    }

    public Integer insertIfNotExist(CardDetailsEntity cardDetailsEntity) {
        Integer cardId;
        if (!isExisted(cardDetailsEntity.getNumber())) {
            cardId = this.cardRepository.insert(cardDetailsEntity);
            LOGGER.log(Level.INFO, cardDetailsEntity.getClass().getName() + LOG_MSG);
        } else {
            cardId = returnId(cardDetailsEntity.getNumber());
        }

        return cardId;
    }

    private boolean isExisted(String number) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM card_details" + " WHERE `number` = " + number);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private Integer returnId(String number) {
        Integer cardId = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement
                ("SELECT card_id FROM card_details" + " WHERE `number` = " + number);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                cardId = resultSet.getInt("card_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return cardId;
    }
}
