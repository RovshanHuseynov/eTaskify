package az.abbbank.cloud.etaskify.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.dto.UpdateEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {EmployeeController.class})
@ExtendWith(SpringExtension.class)
public class EmployeeControllerTest {
    @Autowired
    private EmployeeController employeeController;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() throws Exception {
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
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"surname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"tasks\":[]}"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        when(this.employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee");
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllEmployees2() throws Exception {
        when(this.employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/employee");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testAddEmployee() throws Exception {
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
        when(this.employeeService.addEmployee(anyLong(), (AddEmployeeRequestDTO) any())).thenReturn(employee);

        AddEmployeeRequestDTO addEmployeeRequestDTO = new AddEmployeeRequestDTO();
        addEmployeeRequestDTO.setEmail("jane.doe@example.org");
        addEmployeeRequestDTO.setName("Name");
        addEmployeeRequestDTO.setSurname("Doe");
        String content = (new ObjectMapper()).writeValueAsString(addEmployeeRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/employee/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"surname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"tasks\":[]}"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
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
        when(this.employeeService.updateEmployee((UpdateEmployeeRequestDTO) any())).thenReturn(employee);

        UpdateEmployeeRequestDTO updateEmployeeRequestDTO = new UpdateEmployeeRequestDTO();
        updateEmployeeRequestDTO.setEmail("jane.doe@example.org");
        updateEmployeeRequestDTO.setPassword("iloveyou");
        updateEmployeeRequestDTO.setId(123L);
        updateEmployeeRequestDTO.setName("Name");
        updateEmployeeRequestDTO.setSurname("Doe");
        String content = (new ObjectMapper()).writeValueAsString(updateEmployeeRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"surname\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"tasks\":[]}"));
    }
}

