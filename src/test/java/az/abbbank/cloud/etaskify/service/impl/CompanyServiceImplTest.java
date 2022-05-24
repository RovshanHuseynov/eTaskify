package az.abbbank.cloud.etaskify.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.exception.InvalidCompanyException;
import az.abbbank.cloud.etaskify.exception.InvalidPasswordException;
import az.abbbank.cloud.etaskify.repository.CompanyRepository;
import az.abbbank.cloud.etaskify.util.ValidationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CompanyServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CompanyServiceImplTest {
    @MockBean private CompanyRepository companyRepository;
    @Autowired private CompanyServiceImpl companyServiceImpl;
    @MockBean private ValidationUtil validationUtil;

    @Test
    public void testGetCompanyById() {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        Optional<Company> ofResult = Optional.<Company>of(company);
        when(this.companyRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(company, this.companyServiceImpl.getCompanyById(123L));
        verify(this.companyRepository).findById((Long) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    public void testGetCompanyById2() {
        when(this.companyRepository.findById((Long) any())).thenReturn(Optional.<Company>empty());
        assertThrows(InvalidCompanyException.class, () -> this.companyServiceImpl.getCompanyById(123L));
        verify(this.companyRepository).findById((Long) any());
    }

    @Test
    public void testGetAllCompanies() {
        ArrayList<Company> companyList = new ArrayList<Company>();
        when(this.companyRepository.findAll()).thenReturn(companyList);
        List<Company> actualAllCompanies = this.companyServiceImpl.getAllCompanies();
        assertSame(companyList, actualAllCompanies);
        assertTrue(actualAllCompanies.isEmpty());
        verify(this.companyRepository).findAll();
    }

    @Test
    public void testAddCompany() {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        when(this.companyRepository.save((Company) any())).thenReturn(company);
        assertSame(company, this.companyServiceImpl.addCompany(new AddCompanyRequestDTO()));
        verify(this.companyRepository).save((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    public void testUpdateCompany() throws InvalidPasswordException {
        doNothing().when(this.validationUtil).validatePassword(anyString());

        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        when(this.companyRepository.save((Company) any())).thenReturn(company);

        Company company1 = new Company();
        company1.setEmployees(new ArrayList<Employee>());
        company1.setEmail("jane.doe@example.org");
        company1.setPassword("iloveyou");
        company1.setUsername("janedoe");
        company1.setId(123L);
        company1.setName("Name");
        company1.setPhoneNumber("4105551212");
        company1.setAddress("42 Main St");
        assertSame(company, this.companyServiceImpl.updateCompany(company1));
        verify(this.validationUtil).validatePassword(anyString());
        verify(this.companyRepository).save((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }
}

