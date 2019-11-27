package orgprimeholding.repository;

import orgprimeholding.entities.InvoiceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceRepository extends BaseRepository implements Repository<InvoiceEntity>{
    private static final Logger LOGGER = Logger.getLogger(InvoiceRepository.class.getName());

    public InvoiceRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(InvoiceEntity entity) {
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
    public InvoiceEntity get(int id) {
        String getQuery = "SELECT * FROM invoice WHERE invoice_id = " + id;
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                invoiceEntity.setId(resultSet.getInt("invoice_id"));
                invoiceEntity.setTotal(resultSet.getDouble("total"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateTime = resultSet.getString("datetime");
                invoiceEntity.setDateTime(LocalDateTime.parse(dateTime,formatter));
                invoiceEntity.setPayment(resultSet.getString("payment"));
                invoiceEntity.setCustomerId(resultSet.getInt("customer_id"));
                invoiceEntity.setStoreId(resultSet.getInt("store_id"));
                invoiceEntity.setCardId(resultSet.getInt("card_id"));
                resultSet.close();
                break;
            }
            return invoiceEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }

    public void setStoreId(Integer storeId, Integer invoiceId){
        String query = "UPDATE invoice SET `store_id` = " + storeId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public void setCardId(Integer cardId, Integer invoiceId){
        String query = "UPDATE invoice SET `card_id` = " + cardId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public void setCustomerId(Integer customerId, Integer invoiceId){
        String query = "UPDATE invoice SET `customer_id` = " + customerId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }
}
