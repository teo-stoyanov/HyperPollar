package orgprimeholding.repository;

import orgprimeholding.entities.ReceiptEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptRepository extends BaseRepository {
    private static final Logger LOGGER = Logger.getLogger(ReceiptRepository.class.getName());

    public ReceiptRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(ReceiptEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setDouble(1, entity.getTotal());
            /*This might cause some problems*/
            insertQuery.setString(2, entity.getDateTime().toString());
            insertQuery.setString(3,entity.getPayment());
            insertQuery.executeUpdate();

            Integer id = getId(insertQuery);
            if (id != null) return id;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }

    public void setStoreId(Integer storeId, Integer receiptId){
        String query = "UPDATE receipt SET `store_id` = " + storeId + " WHERE `receipt_id` = " + receiptId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public void setCardId(Integer cardId, Integer receiptId){
        String query = "UPDATE receipt SET `card_id` = " + cardId + " WHERE `receipt_id` = " + receiptId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }

}
