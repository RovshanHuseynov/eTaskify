package az.abbbank.cloud.etaskify.controller;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {
    @Autowired private CompanyService companyService;

    @GetMapping("/company/{id}")
    public Company getCompanyById(@PathVariable("id") long companyId){
        return companyService.getCompanyById(companyId);
    }

    @GetMapping("/company")
    public List<Company> getAllCompanies(){
        return companyService.getAllCompanies();
    }

    @PostMapping("/company")
    public Company addCompany(@RequestBody AddCompanyRequestDTO request){
        return companyService.addCompany(request);
    }

    @PutMapping("/company")
    public Company updateCompany(@RequestBody Company company){
        return companyService.updateCompany(company);
    }
}
