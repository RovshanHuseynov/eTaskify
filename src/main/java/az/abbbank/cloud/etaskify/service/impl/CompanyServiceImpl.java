package az.abbbank.cloud.etaskify.service.impl;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.exception.InvalidCompanyException;
import az.abbbank.cloud.etaskify.model.ErrorMessagesEnum;
import az.abbbank.cloud.etaskify.repository.CompanyRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.util.GeneratorUtil;
import az.abbbank.cloud.etaskify.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private ValidationUtil validationUtil;

    @Override
    public Company getCompanyById(long companyId){
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new InvalidCompanyException(ErrorMessagesEnum.INVALID_COMPANY.getMessage(companyId)));
    }

    @Override
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    @Override
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

    @Override
    public Company updateCompany(Company company){
        validationUtil.validatePassword(company.getPassword());
        return companyRepository.save(company);
    }
}
