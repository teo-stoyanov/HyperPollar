package orgprimeholding.core;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.service.CompanyService;

import java.sql.Connection;
import java.util.List;

public class ModifyDatabase {
    private ModifyDatabase() {
    }

    public static void insertCompaniesToDb(List<CompanyEntity> companyEntities, Connection connection) {
        CompanyService companyService = new CompanyService(connection);
        for (CompanyEntity companyEntity : companyEntities) {
            companyService.insertToDb(companyEntity);
        }
    }

    public static CompanyEntity getCompanyFromDb(Integer companyId, Connection connection){
        CompanyService companyService = new CompanyService(connection);
         return companyService.getFromDb(companyId);
    }
}
