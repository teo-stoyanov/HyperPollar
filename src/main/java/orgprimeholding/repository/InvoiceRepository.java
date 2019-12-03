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

public class InvoiceRepository extends BaseRepository implements Repository<InvoiceEntity> {
    private static final Logger LOGGER = Logger.getLogger(InvoiceRepository.class.getName());
    private static final String INSERT_QUERY = " WHERE `invoice_id` = ";

    public InvoiceRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public Integer insert(InvoiceEntity entity) {
        Integer id = null;
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setDouble(1, entity.getTotal());
            insertQuery.setString(2, entity.getDateTime().toString());
            insertQuery.setString(3, entity.getPayment());
            insertQuery.executeUpdate();
            LOGGER.log(Level.INFO, "Invoice is inserted.");

            id = getId(insertQuery);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return id;
    }


    @Override
    public InvoiceEntity get(int id) {
        String getQuery = "SELECT * FROM invoice WHERE invoice_id = " + id;
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                invoiceEntity.setId(resultSet.getInt("invoice_id"));
                invoiceEntity.setTotal(resultSet.getDouble("total"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateTime = resultSet.getString("datetime");
                invoiceEntity.setDateTime(LocalDateTime.parse(dateTime, formatter));
                invoiceEntity.setPayment(resultSet.getString("payment"));
                break;
            }
            return invoiceEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    public Integer getCardIdWithInvoiceId(Integer invoiceId){
        Integer cardId = null;
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
                ("SELECT card_id FROM invoice WHERE `invoice_id` = " + invoiceId);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                cardId = resultSet.getInt("card_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return cardId;
    }

    public Integer getCustomerIdWithInvoiceId(Integer invoiceId){
        Integer cardId = null;
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
                ("SELECT customer_id FROM invoice WHERE `invoice_id` = " + invoiceId);
             ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()) {
                cardId = resultSet.getInt("customer_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return cardId;
    }

    public void setStoreId(Integer storeId, Integer invoiceId) {
        String query = "UPDATE invoice SET `store_id` = " + storeId + INSERT_QUERY + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setCardId(Integer cardId, Integer invoiceId) {
        String query = "UPDATE invoice SET `card_id` = " + cardId + INSERT_QUERY + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setCustomerId(Integer customerId, Integer invoiceId) {
        String query = "UPDATE invoice SET `customer_id` = " + customerId + INSERT_QUERY + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
