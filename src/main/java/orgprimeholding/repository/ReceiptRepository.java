package orgprimeholding.repository;

import orgprimeholding.entities.ReceiptEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiptRepository extends BaseRepository implements Repository<ReceiptEntity>{
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

    @Override
    public ReceiptEntity get(int id) {
        String getQuery = "SELECT * FROM receipt WHERE receipt_id = " + id;
        ReceiptEntity receiptEntity = new ReceiptEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                receiptEntity.setId(resultSet.getInt("receipt_id"));
                receiptEntity.setTotal(resultSet.getDouble("total"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateTime = resultSet.getString("datetime");
                receiptEntity.setDateTime(LocalDateTime.parse(dateTime,formatter));
                receiptEntity.setPayment(resultSet.getString("payment"));
                receiptEntity.setCardId(resultSet.getInt("card_id"));
                receiptEntity.setStoreId(resultSet.getInt("store_id"));
                resultSet.close();
                break;
            }
            return receiptEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
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
