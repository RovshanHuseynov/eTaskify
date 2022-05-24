package az.abbbank.cloud.etaskify.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
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
    public void testGetEmployeeById() throws InvalidEmployeeException {
        doNothing().when(this.validationUtil).validateEmployee((Optional<Employee>) any());

        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setPassword("iloveyou");
        employee.setId(123L);
        employee.setName("Name");
        employee.setCompany(company);
        employee.setSurname("Doe");
        employee.setTasks(new ArrayList<Task>());
        Optional<Employee> ofResult = Optional.<Employee>of(employee);
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(employee, this.employeeServiceImpl.getEmployeeById(123L));
        verify(this.validationUtil).validateEmployee((Optional<Employee>) any());
        verify(this.employeeRepository).findById((Long) any());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testGetEmployeeById2() throws InvalidEmployeeException {
        doNothing().when(this.validationUtil).validateEmployee((Optional<Employee>) any());

        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setPassword("iloveyou");
        employee.setId(123L);
        employee.setName("Name");
        employee.setCompany(company);
        employee.setSurname("Doe");
        employee.setTasks(new ArrayList<Task>());
        Optional<Employee> ofResult = Optional.<Employee>of(employee);
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(employee, this.employeeServiceImpl.getEmployeeById(0L));
        verify(this.validationUtil).validateEmployee((Optional<Employee>) any());
        verify(this.employeeRepository).findById((Long) any());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testGetAllEmployees() {
        ArrayList<Employee> employeeList = new ArrayList<Employee>();
        when(this.employeeRepository.findAll()).thenReturn(employeeList);
        List<Employee> actualAllEmployees = this.employeeServiceImpl.getAllEmployees();
        assertSame(employeeList, actualAllEmployees);
        assertTrue(actualAllEmployees.isEmpty());
        verify(this.employeeRepository).findAll();
    }

    @Test
    public void testAddEmployee() {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setPassword("iloveyou");
        employee.setId(123L);
        employee.setName("Name");
        employee.setCompany(company);
        employee.setSurname("Doe");
        employee.setTasks(new ArrayList<Task>());
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee);

        Company company1 = new Company();
        company1.setEmployees(new ArrayList<Employee>());
        company1.setEmail("jane.doe@example.org");
        company1.setPassword("iloveyou");
        company1.setUsername("janedoe");
        company1.setId(123L);
        company1.setName("Name");
        company1.setPhoneNumber("4105551212");
        company1.setAddress("42 Main St");
        when(this.companyService.getCompanyById(anyLong())).thenReturn(company1);
        assertSame(employee,
                this.employeeServiceImpl.addEmployee(123L, new AddEmployeeRequestDTO("Name", "Doe", "jane.doe@example.org")));
        verify(this.employeeRepository).save((Employee) any());
        verify(this.companyService).getCompanyById(anyLong());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }

    @Test
    public void testUpdateEmployee() throws InvalidEmployeeException, InvalidPasswordException {
        doNothing().when(this.validationUtil).validatePassword(anyString());
        doNothing().when(this.validationUtil).validateEmployee((Optional<Employee>) any());

        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setPassword("iloveyou");
        employee.setId(123L);
        employee.setName("Name");
        employee.setCompany(company);
        employee.setSurname("Doe");
        employee.setTasks(new ArrayList<Task>());
        Optional<Employee> ofResult = Optional.<Employee>of(employee);

        Company company1 = new Company();
        company1.setEmployees(new ArrayList<Employee>());
        company1.setEmail("jane.doe@example.org");
        company1.setPassword("iloveyou");
        company1.setUsername("janedoe");
        company1.setId(123L);
        company1.setName("Name");
        company1.setPhoneNumber("4105551212");
        company1.setAddress("42 Main St");

        Employee employee1 = new Employee();
        employee1.setEmail("jane.doe@example.org");
        employee1.setPassword("iloveyou");
        employee1.setId(123L);
        employee1.setName("Name");
        employee1.setCompany(company1);
        employee1.setSurname("Doe");
        employee1.setTasks(new ArrayList<Task>());
        when(this.employeeRepository.save((Employee) any())).thenReturn(employee1);
        when(this.employeeRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(employee1, this.employeeServiceImpl
                .updateEmployee(new UpdateEmployeeRequestDTO(123L, "Name", "Doe", "jane.doe@example.org", "iloveyou")));
        verify(this.validationUtil).validateEmployee((Optional<Employee>) any());
        verify(this.validationUtil).validatePassword(anyString());
        verify(this.employeeRepository).findById((Long) any());
        verify(this.employeeRepository).save((Employee) any());
        assertTrue(this.employeeServiceImpl.getAllEmployees().isEmpty());
    }
}

