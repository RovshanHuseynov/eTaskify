package az.abbbank.cloud.etaskify.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.dto.UpdateEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.exception.InvalidPasswordException;
import az.abbbank.cloud.etaskify.repository.EmployeeRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
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

@ContextConfiguration(classes = {EmployeeServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class EmployeeServiceImplTest {
    @MockBean
    private CompanyService companyService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @MockBean
    private ValidationUtil validationUtil;

    @Test
    public void testGetEmployeeById() {
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

        Employee employee = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .company(company)
                .surname("Doe")
                .tasks(new ArrayList<Task>())
                .build();
        Optional<Employee> ofResult = Optional.<Employee>of(employee);

        // when
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);

        // then
        assertSame(employee, this.employeeServiceImpl.getEmployeeById(123L));
        verify(this.employeeRepository).findById((Long) any());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testGetEmployeeById2() {
        // when
        when(this.employeeRepository.findById((Long) any())).thenReturn(Optional.<Employee>empty());

        // then
        assertThrows(InvalidEmployeeException.class, () -> this.employeeServiceImpl.getEmployeeById(123L));
        verify(this.employeeRepository).findById((Long) any());
    }

    @Test
    public void testGetAllEmployees() {
        // given
        ArrayList<Employee> employeeList = new ArrayList<Employee>();

        // when
        when(this.employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualAllEmployees = this.employeeServiceImpl.getAllEmployees();

        // then
        assertSame(employeeList, actualAllEmployees);
        assertTrue(actualAllEmployees.isEmpty());
        verify(this.employeeRepository).findAll();
    }

    @Test
    public void testAddEmployee() {
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

        Employee employee = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .company(company)
                .surname("Doe")
                .tasks(new ArrayList<Task>())
                .build();

        // when
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee);

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

        when(this.companyService.getCompanyById(anyLong())).thenReturn(company1);

        // then
        assertSame(employee,
                this.employeeServiceImpl.addEmployee(123L, new AddEmployeeRequestDTO("Name", "Doe", "jane.doe@example.org")));
        verify(this.employeeRepository).save((Employee) any());
        verify(this.companyService).getCompanyById(anyLong());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testUpdateEmployee() throws InvalidPasswordException {
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

        Employee employee = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .company(company)
                .surname("Doe")
                .tasks(new ArrayList<Task>())
                .build();

        Optional<Employee> ofResult = Optional.<Employee>of(employee);

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

        Employee employee1 = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .company(company1)
                .surname("Doe")
                .tasks(new ArrayList<Task>())
                .build();

        // when
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);

        // then
        assertSame(employee1, this.employeeServiceImpl
                .updateEmployee(new UpdateEmployeeRequestDTO(123L, "Name", "Doe", "jane.doe@example.org", "iloveyou")));
        verify(this.validationUtil).validatePassword(anyString());
        verify(this.employeeRepository).findById((Long) any());
        verify(this.employeeRepository).save((Employee) any());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testUpdateEmployee2() throws InvalidPasswordException {
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

        Employee employee = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .company(company)
                .surname("Doe")
                .tasks(new ArrayList<Task>())
                .build();

        // when
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee);
        when(this.employeeRepository.findById((Long) any())).thenReturn(Optional.<Employee>empty());

        // then
        assertThrows(InvalidEmployeeException.class, () -> this.employeeServiceImpl
                .updateEmployee(new UpdateEmployeeRequestDTO(123L, "Name", "Doe", "jane.doe@example.org", "iloveyou")));
        verify(this.employeeRepository).findById((Long) any());
    }
}

