package orgprimeholding.service.repository.service;

import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.StoreRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreService {
    private static final Logger LOGGER = Logger.getLogger(StoreService.class.getName());
    private static final String LOG_MSG = " store is stored in DB.";

    private StoreRepository storeRepository;
    private Connection connection;

    public StoreService(Connection connection) {
        this.connection = connection;
        this.storeRepository = new StoreRepository(StoreEntity.class, connection);
    }


    public Integer insertIfNotExist(StoreEntity storeEntity, Integer companyId) {
        Integer storeId = null;

        try {
            if (!isExisted(storeEntity.getName(), storeEntity.getAddress())) {
                storeId = this.storeRepository.insert(storeEntity);
                LOGGER.log(Level.INFO, storeEntity.getName() + LOG_MSG);
            } else {
                storeId = returnId(storeEntity.getName(), storeEntity.getAddress());
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        this.storeRepository.setCompanyId(companyId, storeId);
        return storeId;
    }

    private boolean isExisted(String name, String address) throws SQLException {
        ResultSet resultSet = getResultSet(name, address);
        return resultSet.next();
    }

    private Integer returnId(String name, String address) throws SQLException {
        ResultSet resultSet = getResultSet(name, address);

        Integer storeId = null;
        while (resultSet.next()) {
            storeId = resultSet.getInt("store_id");
        }
        return storeId;
    }

    private ResultSet getResultSet(String name, String address) throws SQLException {
        PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT store_id FROM store" + " WHERE `name` = ? AND `address` = ?;");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        return preparedStatement.executeQuery();
    }
}
