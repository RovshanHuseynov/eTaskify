package az.abbbank.cloud.etaskify.service.impl;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.exception.InvalidCompanyException;
import az.abbbank.cloud.etaskify.exception.InvalidPasswordException;
import az.abbbank.cloud.etaskify.repository.CompanyRepository;
import az.abbbank.cloud.etaskify.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CompanyServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class CompanyServiceImplTest {
    @MockBean private CompanyRepository companyRepository;
    @Autowired private CompanyServiceImpl companyServiceImpl;
    @MockBean private ValidationUtil validationUtil;

    @Test
    public void testGetCompanyById() {
        //given
        Company company = Company.builder()
                .employees(new ArrayList<Employee>())
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .id(123L)
                .name("Name")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .build();

        // when
        Optional<Company> ofResult = Optional.<Company>of(company);
        when(this.companyRepository.findById((Long) any())).thenReturn(ofResult);

        // then
        assertSame(company, this.companyServiceImpl.getCompanyById(123L));
        verify(this.companyRepository).findById((Long) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    public void testGetCompanyById2() {
        // when
        when(this.companyRepository.findById((Long) any())).thenReturn(Optional.<Company>empty());

        // then
        assertThrows(InvalidCompanyException.class, () -> this.companyServiceImpl.getCompanyById(123L));
        verify(this.companyRepository).findById((Long) any());
    }

    @Test
    public void testGetAllCompanies() {
        // given
        ArrayList<Company> companyList = new ArrayList<Company>();

        // when
        when(this.companyRepository.findAll()).thenReturn(companyList);

        // then
        List<Company> actualAllCompanies = this.companyServiceImpl.getAllCompanies();
        assertSame(companyList, actualAllCompanies);
        assertTrue(actualAllCompanies.isEmpty());
        verify(this.companyRepository).findAll();
    }

    @Test
    public void testAddCompany() {
        // given
        Company company = Company.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .employees(new ArrayList<Employee>())
                .build();

        // when
        when(this.companyRepository.save((Company) any())).thenReturn(company);

        // then
        assertSame(company, this.companyServiceImpl.addCompany(new AddCompanyRequestDTO()));
        verify(this.companyRepository).save((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }

    @Test
    public void testUpdateCompany() throws InvalidPasswordException {
        // given
        doNothing().when(this.validationUtil).validatePassword(anyString());

        Company company = Company.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .employees(new ArrayList<Employee>())
                .build();

        // when
        when(this.companyRepository.save((Company) any())).thenReturn(company);

        // then
        Company company1 = Company.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .employees(new ArrayList<Employee>())
                .build();

        assertSame(company, this.companyServiceImpl.updateCompany(company1));
        verify(this.validationUtil).validatePassword(anyString());
        verify(this.companyRepository).save((Company) any());
        assertTrue(this.companyServiceImpl.getAllCompanies().isEmpty());
    }
}

