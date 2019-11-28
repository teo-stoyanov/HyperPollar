package orgprimeholding.repository;

import orgprimeholding.entities.CardDetailsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardRepository extends BaseRepository implements Repository<CardDetailsEntity> {
    private static final Logger LOGGER = Logger.getLogger(CardRepository.class.getName());

    public CardRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public Integer insert(CardDetailsEntity entity) {
        Integer id = null;
        if (!exist(entity.getNumber())) {
            try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
                insertQuery.setString(1, entity.getCardType());
                insertQuery.setString(2, entity.getNumber());
                insertQuery.setBoolean(3, entity.isContactless());
                insertQuery.executeUpdate();

                id = getId(insertQuery);
                LOGGER.log(Level.INFO, "Card inserted.");
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            id = returnId(entity.getNumber());
        }
        return id;
    }

    @Override
    public CardDetailsEntity get(int id) {
        String getQuery = "SELECT * FROM card_details WHERE card_id = " + id;
        CardDetailsEntity cardDetailsEntity = new CardDetailsEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                cardDetailsEntity.setId(resultSet.getInt("card_id"));
                cardDetailsEntity.setCardType(resultSet.getString("cardtype"));
                cardDetailsEntity.setNumber(resultSet.getString("number"));
                cardDetailsEntity.setContactless(resultSet.getBoolean("contactless"));
                break;
            }
            return cardDetailsEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private boolean exist(String number) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
                ("SELECT * FROM card_details" + " WHERE `number` = " + number);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private Integer returnId(String number) {
        Integer cardId = null;
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
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
