package orgprimeholding.service.repository.service;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.repository.CompanyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyService {
    private static final Logger LOGGER = Logger.getLogger(CompanyService.class.getName());
    private static final String LOG_MSG = " company is stored in DB.";

    private CompanyRepository companyRepository;
    private Connection connection;

    public CompanyService(Connection connection) {
        this.connection = connection;
        this.companyRepository = new CompanyRepository(CompanyEntity.class, connection);
    }

    public Integer insertIfNotExist(CompanyEntity companyEntity) {
        Integer companyId;
        if (!isExisted(companyEntity.getUuid())) {
            companyId = companyRepository.insert(companyEntity);
            LOGGER.log(Level.INFO, companyEntity.getName() + LOG_MSG);
        } else {
            companyId = returnId(companyEntity.getUuid());
        }

        return companyId;
    }

    private boolean isExisted(String uuid) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT * FROM company" + " WHERE `uuid` = " + uuid);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    private Integer returnId(String uuid) {
        Integer companyId = null;
        try (PreparedStatement preparedStatement = this.connection.prepareStatement("SELECT company_id FROM company" + " WHERE `uuid` = " + uuid);
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
