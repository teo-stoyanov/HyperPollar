package orgprimeholding.repository;

import orgprimeholding.entities.StoreEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreRepository extends BaseRepository implements Repository<StoreEntity> {
    private static final Logger LOGGER = Logger.getLogger(StoreRepository.class.getName());
    private static final String LOG_MSG = " store is stored in DB.";

    public StoreRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public Integer insert(StoreEntity entity) {
        Integer id = null;
        if (!isExisted(entity.getName(), entity.getAddress())) {
            try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
                insertQuery.setString(1, entity.getName());
                insertQuery.setString(2, entity.getAddress());
                insertQuery.executeUpdate();
                LOGGER.log(Level.INFO, entity.getName() + LOG_MSG);

                id = getId(insertQuery);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            id = returnId(entity.getName(), entity.getAddress());
        }
        return id;
    }

    @Override
    public StoreEntity get(int id) {
        String getQuery = "SELECT * FROM store WHERE store_id = " + id;
        StoreEntity storeEntity = new StoreEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                storeEntity.setId(resultSet.getInt("store_id"));
                storeEntity.setName(resultSet.getString("name"));
                storeEntity.setAddress(resultSet.getString("address"));
                storeEntity.setCompanyId(resultSet.getInt("company_id"));
                break;
            }
            return storeEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    public void setCompanyId(Integer companyId, Integer storeId) {
        String query = "UPDATE store SET `company_id` = " + companyId + " WHERE `store_id` = " + storeId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }


    private boolean isExisted(String name, String address) {
        try {
            ResultSet resultSet = getResultSet(name, address);
            assert resultSet != null;
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return false;
    }

    private ResultSet getResultSet(String name, String address) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
                ("SELECT store_id FROM store" + " WHERE `name` = " + name + " AND `address` = " + address)) {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private Integer returnId(String name, String address) {
        Integer storeId = null;
        try {
            ResultSet resultSet = getResultSet(name, address);
            assert resultSet != null;
            while (resultSet.next()) {
                storeId = resultSet.getInt("store_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return storeId;
    }
}
