package orgprimeholding.repository;

import orgprimeholding.entities.CompanyEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyRepository extends BaseRepository implements Repository<CompanyEntity>{
    private static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());

    public CompanyRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public int insert(CompanyEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setString(1, entity.getName());
            insertQuery.setString(2, entity.getAddress());
            insertQuery.setString(3, entity.getUuid());
            insertQuery.executeUpdate();

            Integer id = getId(insertQuery);
            if (id != null) return id;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return -1;
    }

    @Override
    public CompanyEntity get(int id) {
        String getQuery = "SELECT * FROM company WHERE company_id = " + id;
        CompanyEntity companyEntity = new CompanyEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                companyEntity.setId(resultSet.getInt("company_id"));
                companyEntity.setName(resultSet.getString("name"));
                companyEntity.setAddress(resultSet.getString("address"));
                companyEntity.setUuid(resultSet.getString("uuid"));
                resultSet.close();
                break;
            }
            return companyEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
        }
        return null;
    }
}
