package orgprimeholding.repository;

import orgprimeholding.entities.ReceiptEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptRepository extends BaseRepository implements Repository<ReceiptEntity> {
    private static final Logger LOGGER = Logger.getLogger(ReceiptRepository.class.getName());
    private int id;

    public ReceiptRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public void insert(ReceiptEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setDouble(1, entity.getTotal());
            /*This might cause some problems*/
            insertQuery.setString(2, entity.getDateTime().toString());
            insertQuery.setString(3, entity.getPayment());
            insertQuery.executeUpdate();

            this.id = getId(insertQuery);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setStoreId(Integer storeId, Integer receiptId) {
        String query = "UPDATE receipt SET `store_id` = " + storeId + " WHERE `receipt_id` = " + receiptId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setCardId(Integer cardId, Integer receiptId) {
        String query = "UPDATE receipt SET `card_id` = " + cardId + " WHERE `receipt_id` = " + receiptId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

}
