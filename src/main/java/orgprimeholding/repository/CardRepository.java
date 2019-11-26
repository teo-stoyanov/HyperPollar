package orgprimeholding.repository;

import orgprimeholding.entities.CardDetailsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardRepository extends BaseRepository implements Repository<CardDetailsEntity>{
    private static final Logger LOGGER = Logger.getLogger(CardRepository.class.getName());

    public CardRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(CardDetailsEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setString(1, entity.getCardType());
            insertQuery.setString(2, entity.getNumber());
            insertQuery.setBoolean(3, entity.isContactless());
            insertQuery.executeUpdate();

            Integer id = getId(insertQuery);
            if (id != null) return id;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }
}
