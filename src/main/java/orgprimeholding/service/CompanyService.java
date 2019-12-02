package orgprimeholding.service;

import orgprimeholding.entities.CompanyEntity;
import orgprimeholding.entities.StoreEntity;
import orgprimeholding.repository.CompanyRepository;

import java.sql.Connection;
import java.util.List;

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

    public CompanyEntity getFromDb(Integer id) {
        StoreService storeService = new StoreService(this.connection);
        CompanyEntity companyEntity = this.companyRepository.get(id);
        List<Integer> storeIds = this.companyRepository.getStoreIds(companyEntity.getId());
        for (Integer storeId : storeIds) {
            companyEntity.getStores().add(storeService.getFromDb(storeId));
        }
        return companyEntity;
    }
}
