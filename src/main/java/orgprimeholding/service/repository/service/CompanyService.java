package orgprimeholding.service.repository.service;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.CompanyRepository;

import java.sql.Connection;

public class CompanyService {
    private CompanyRepository companyRepository;
    private Connection connection;

    public CompanyService(Connection connection) {
        this.connection = connection;
        this.companyRepository = new CompanyRepository(CompanyEntity.class, connection);
    }

    public void insertToDb(CompanyEntity companyEntity) {
        StoreService storeService = new StoreService(this.connection);
        Integer companyId = this.companyRepository.insert(companyEntity);
        for (StoreEntity store : companyEntity.getStores()) {
            storeService.insertToDb(store, companyId);
        }
    }
}
