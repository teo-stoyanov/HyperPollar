package orgprimeholding.core;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.service.repository.service.CompanyService;

import java.sql.Connection;
import java.util.List;

public class EntitiesToDb {
    private EntitiesToDb() {
    }

    public static void insertCompaniesToDb(List<CompanyEntity> companyEntities, Connection connection) {
        CompanyService companyService = new CompanyService(connection);
        for (CompanyEntity companyEntity : companyEntities) {
            companyService.insertToDb(companyEntity);
        }
    }
}
