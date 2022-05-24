package az.abbbank.cloud.etaskify.service;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;

import java.util.List;

public interface CompanyService {
    Company getCompanyById(long companyId);

    List<Company> getAllCompanies();

    Company addCompany(AddCompanyRequestDTO request);

    Company updateCompany(Company company);
}
