package orgprimeholding.repository;

import orgprimeholding.entities.CompanyEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyRepository extends BaseRepository implements Repository<CompanyEntity>{
    private static final Logger LOGGER = Logger.getLogger(CompanyRepository.class.getName());
    private int id;
    public CompanyRepository(Class entity, Connection connection) {
        super(entity, connection);
        this.id = 0;
    }

    public void insert(CompanyEntity entity) {
        try (PreparedStatement insertQuery = super.getConnection().prepareStatement(super.insertQuery(), Statement.RETURN_GENERATED_KEYS)) {
            insertQuery.setString(1, entity.getName());
            insertQuery.setString(2, entity.getAddress());
            insertQuery.setString(3, entity.getUuid());
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


}
