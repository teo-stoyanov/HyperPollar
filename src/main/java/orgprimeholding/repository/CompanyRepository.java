package orgprimeholding.repository;

import orgprimeholding.entities.CompanyEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyRepository extends BaseRepository implements Repository<CompanyEntity> {
    private static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());
    private static final String LOG_MSG = " company is stored in DB.";

    public CompanyRepository(Class entity, Connection connection) {
        super(entity, connection);
    }

    public Integer insert(CompanyEntity entity) {
        Integer id = null;
        if (!exist(entity.getUuid())) {
            try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
                insertQuery.setString(1, entity.getName());
                insertQuery.setString(2, entity.getAddress());
                insertQuery.setString(3, entity.getUuid());
                insertQuery.executeUpdate();
                LOGGER.log(Level.INFO, entity.getName() + LOG_MSG);

                id = getId(insertQuery);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        } else {
            id = returnId(entity.getUuid());
        }

        return id;
    }

    @Override
    public CompanyEntity get(int id) {
        String getQuery = "SELECT * FROM company WHERE company_id = " + id;
        CompanyEntity companyEntity = new CompanyEntity();
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement(getQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                companyEntity.setId(resultSet.getInt("company_id"));
                companyEntity.setName(resultSet.getString("name"));
                companyEntity.setAddress(resultSet.getString("address"));
                companyEntity.setUuid(resultSet.getString("uuid"));
                break;
            }
            return companyEntity;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return null;
    }

    private boolean exist(String uuid) {
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement("SELECT * FROM company" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    private Integer returnId(String uuid) {
        Integer companyId = null;
        try (PreparedStatement preparedStatement = super.getConnection().prepareStatement
                ("SELECT company_id FROM company" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                companyId = resultSet.getInt("company_id");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return companyId;
    }
}
