package orgprimeholding.repository;

import orgprimeholding.entities.InvoiceEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InvoiceRepository extends BaseRepository implements Repository<InvoiceEntity> {
    private static final Logger LOGGER = Logger.getLogger(InvoiceRepository.class.getName());
    private int id;

    public InvoiceRepository(Class entity, Connection connection) {
        super(entity, connection);
        this.id = 0;
    }

    public void insert(InvoiceEntity entity) {
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

    public void setStoreId(Integer storeId, Integer invoiceId) {
        String query = "UPDATE invoice SET `store_id` = " + storeId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setCardId(Integer cardId, Integer invoiceId) {
        String query = "UPDATE invoice SET `card_id` = " + cardId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setCustomerId(Integer customerId, Integer invoiceId) {
        String query = "UPDATE invoice SET `customer_id` = " + customerId + " WHERE `invoice_id` = " + invoiceId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
