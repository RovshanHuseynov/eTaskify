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
        Company company = Company.builder()
                .id(123L)
                .name("cybernet")
                .email("system@cybernet.az")
                .password("cybernet123")
                .username("admincybernet")
                .phoneNumber("01245632423")
                .address("xetai street")
                .employees(new ArrayList<Employee>())
                .build();

        Employee employee = Employee.builder()
                .id(123L)
                .name("rovshan")
                .surname("huseynov")
                .email("rovshan.huseynov@cybernet.az")
                .password("rovshan123")
                .tasks(new ArrayList<Task>())
                .company(company)
                .build();

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
        Company company = Company.builder()
                .id(123L)
                .name("abb")
                .email("system@abb-bank.az")
                .password("abb123")
                .username("adminabb")
                .phoneNumber("937")
                .address("nizami street")
                .employees(new ArrayList<Employee>())
                .build();

        Employee employee = Employee.builder()
                .id(123L)
                .name("ali")
                .surname("alili")
                .email("ali.alili@abb-bank.az")
                .password("ali123")
                .tasks(new ArrayList<Task>())
                .company(company)
                .build();

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
        Company company = Company.builder()
                .id(123L)
                .name("azericard")
                .email("system@azericard.com")
                .password("azericard123")
                .username("adminazericard")
                .phoneNumber("012435352342")
                .address("bulbul street")
                .employees(new ArrayList<Employee>())
                .build();

        Employee employee = Employee.builder()
                .id(123L)
                .name("semed")
                .surname("semedov")
                .email("semed.semedov@azericard.com")
                .password("semed123")
                .tasks(new ArrayList<Task>())
                .company(company)
                .build();

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

