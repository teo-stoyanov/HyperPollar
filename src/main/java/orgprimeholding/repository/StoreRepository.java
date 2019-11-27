package orgprimeholding.repository;

import orgprimeholding.entities.StoreEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StoreRepository extends BaseRepository implements Repository<StoreEntity>{
    private static final Logger LOGGER = Logger.getLogger(StoreRepository.class.getName());

    public StoreRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(StoreEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setString(1, entity.getName());
            insertQuery.setString(2, entity.getAddress());
            insertQuery.executeUpdate();

            Integer id = getId(insertQuery);
            if (id != null) return id;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }

    @Override
    public StoreEntity get(int id) {
        String getQuery = "SELECT * FROM store WHERE store_id = " + id;
        StoreEntity storeEntity = new StoreEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                storeEntity.setId(resultSet.getInt("store_id"));
                storeEntity.setName(resultSet.getString("name"));
                storeEntity.setAddress(resultSet.getString("address"));
                storeEntity.setCompanyId(resultSet.getInt("company_id"));
                resultSet.close();
                break;
            }
            return storeEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }

    public void setCompanyId(Integer companyId, Integer storeId){
        String query = "UPDATE store SET `company_id` = " + companyId + " WHERE `store_id` = " + storeId + ";";
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(query)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
    }
}
