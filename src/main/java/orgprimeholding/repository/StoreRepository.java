package orgprimeholding.repository;

import orgprimeholding.entities.StoreEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreRepository extends BaseRepository implements Repository<StoreEntity> {
    private static final Logger LOGGER = Logger.getLogger(StoreRepository.class.getName());
    private int id;

    public StoreRepository(Class entity, Connection connection) {
        super(entity, connection);
        this.id = 0;
    }

    public void insert(StoreEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setString(1, entity.getName());
            insertQuery.setString(2, entity.getAddress());
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

    public void setCompanyId(Integer companyId, Integer storeId) {
        String query = "UPDATE store SET `company_id` = " + companyId + " WHERE `store_id` = " + storeId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
