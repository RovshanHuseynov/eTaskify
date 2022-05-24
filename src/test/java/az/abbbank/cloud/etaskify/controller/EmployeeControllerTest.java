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
    @Autowired private EmployeeController employeeController;
    @MockBean private EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() throws Exception {
        // given
        Company company = new Company();
        company.setId(123L);
        company.setName("cybernet");
        company.setEmail("system@cybernet.az");
        company.setPassword("cybernet123");
        company.setUsername("admincybernet");
        company.setPhoneNumber("01245632423");
        company.setAddress("xetai street");
        company.setEmployees(new ArrayList<Employee>());

        Employee employee = new Employee();
        employee.setId(123L);
        employee.setName("rovshan");
        employee.setSurname("huseynov");
        employee.setEmail("rovshan.huseynov@cybernet.az");
        employee.setPassword("rovshan123");
        employee.setTasks(new ArrayList<Task>());
        employee.setCompany(company);

        // when
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);

        // then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/employee/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.employeeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"rovshan\",\"surname\":\"huseynov\",\"email\":\"rovshan.huseynov@cybernet.az\",\"password\":\"rovshan123\","
                                        + "\"tasks\":[]}"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        // when
        when(this.employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());

        // then
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
        // when
        when(this.employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());

        // then
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
        // given
        Company company = new Company();
        company.setId(123L);
        company.setName("abb");
        company.setEmail("system@abb-bank.az");
        company.setPassword("abb123");
        company.setUsername("adminabb");
        company.setPhoneNumber("937");
        company.setAddress("nizami street");
        company.setEmployees(new ArrayList<Employee>());

        Employee employee = new Employee();
        employee.setId(123L);
        employee.setName("ali");
        employee.setSurname("alili");
        employee.setEmail("ali.alili@abb-bank.az");
        employee.setPassword("ali123");
        employee.setTasks(new ArrayList<Task>());
        employee.setCompany(company);

        // when
        when(this.employeeService.addEmployee(anyLong(), (AddEmployeeRequestDTO) any())).thenReturn(employee);

        // then
        AddEmployeeRequestDTO addEmployeeRequestDTO = new AddEmployeeRequestDTO();
        addEmployeeRequestDTO.setName("new name");
        addEmployeeRequestDTO.setSurname("new surname");
        addEmployeeRequestDTO.setEmail("new email");
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
                                "{\"id\":123,\"name\":\"ali\",\"surname\":\"alili\",\"email\":\"ali.alili@abb-bank.az\",\"password\":\"ali123\","
                                        + "\"tasks\":[]}"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        // given
        Company company = new Company();
        company.setId(123L);
        company.setName("azericard");
        company.setEmail("system@azericard.com");
        company.setPassword("azericard123");
        company.setUsername("adminazericard");
        company.setPhoneNumber("012435352342");
        company.setAddress("bulbul street");
        company.setEmployees(new ArrayList<Employee>());

        Employee employee = new Employee();
        employee.setId(123L);
        employee.setName("semed");
        employee.setSurname("semedov");
        employee.setEmail("semed.semedov@azericard.com");
        employee.setPassword("semed123");
        employee.setTasks(new ArrayList<Task>());
        employee.setCompany(company);

        // when
        when(this.employeeService.updateEmployee((UpdateEmployeeRequestDTO) any())).thenReturn(employee);

        // then
        UpdateEmployeeRequestDTO updateEmployeeRequestDTO = new UpdateEmployeeRequestDTO();
        updateEmployeeRequestDTO.setId(123L);
        updateEmployeeRequestDTO.setName("new name");
        updateEmployeeRequestDTO.setSurname("new surname");
        updateEmployeeRequestDTO.setEmail("new email");
        updateEmployeeRequestDTO.setPassword("new password");
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
                                "{\"id\":123,\"name\":\"semed\",\"surname\":\"semedov\",\"email\":\"semed.semedov@azericard.com\",\"password\":\"semed123\","
                                        + "\"tasks\":[]}"));
    }
}

