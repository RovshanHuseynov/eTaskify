package az.abbbank.cloud.etaskify.service.impl;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.repository.CompanyRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.util.GeneratorUtil;
import az.abbbank.cloud.etaskify.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = { Exception.class })
public class CompanyServiceImpl implements CompanyService {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private ValidationUtil validationUtil;

    public Company getCompanyById(long companyId){
        Optional<Company> company = companyRepository.findById(companyId);
        validationUtil.validateCompany(company);
        return company.get();
    }

    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public Company addCompany(AddCompanyRequestDTO request){
        Company company = Company.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .email(request.getEmail())
                .username(GeneratorUtil.generateCompanyUsername(request.getName()))
                .password(GeneratorUtil.generatePassword(8))
                .employees(request.getEmployees())
                .build();
        return companyRepository.save(company);
    }

    public Company updateCompany(Company company){
        validationUtil.validatePassword(company.getPassword());
        return companyRepository.save(company);
    }
}
