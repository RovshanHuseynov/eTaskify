package az.abbbank.cloud.etaskify.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.service.impl.CompanyServiceImpl;
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

@ContextConfiguration(classes = {CompanyController.class})
@ExtendWith(SpringExtension.class)
public class CompanyControllerTest {
    @Autowired
    private CompanyController companyController;

    @MockBean
    private CompanyService companyService;

    @Test
    public void testGetCompanyById() throws Exception {
        // given
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        // when
        when(this.companyService.getCompanyById(anyLong())).thenReturn(company);

        // then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.companyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"phoneNumber\":\"4105551212\",\"address\":\"42 Main St\",\"email\":\"jane.doe@example"
                                        + ".org\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"employees\":[]}"));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        when(this.companyService.getAllCompanies()).thenReturn(new ArrayList<Company>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/company");
        MockMvcBuilders.standaloneSetup(this.companyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllCompanies2() throws Exception {
        when(this.companyService.getAllCompanies()).thenReturn(new ArrayList<Company>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/company");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.companyController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testAddCompany() throws Exception {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        when(this.companyService.addCompany((AddCompanyRequestDTO) any())).thenReturn(company);

        AddCompanyRequestDTO addCompanyRequestDTO = new AddCompanyRequestDTO();
        addCompanyRequestDTO.setEmployees(new ArrayList<Employee>());
        addCompanyRequestDTO.setEmail("jane.doe@example.org");
        addCompanyRequestDTO.setName("Name");
        addCompanyRequestDTO.setPhoneNumber("4105551212");
        addCompanyRequestDTO.setAddress("42 Main St");
        String content = (new ObjectMapper()).writeValueAsString(addCompanyRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.companyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"phoneNumber\":\"4105551212\",\"address\":\"42 Main St\",\"email\":\"jane.doe@example"
                                        + ".org\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"employees\":[]}"));
    }

    @Test
    public void testUpdateCompany() throws Exception {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        when(this.companyService.updateCompany((Company) any())).thenReturn(company);

        Company company1 = new Company();
        company1.setEmployees(new ArrayList<Employee>());
        company1.setEmail("jane.doe@example.org");
        company1.setPassword("iloveyou");
        company1.setUsername("janedoe");
        company1.setId(123L);
        company1.setName("Name");
        company1.setPhoneNumber("4105551212");
        company1.setAddress("42 Main St");
        String content = (new ObjectMapper()).writeValueAsString(company1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.companyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"name\":\"Name\",\"phoneNumber\":\"4105551212\",\"address\":\"42 Main St\",\"email\":\"jane.doe@example"
                                        + ".org\",\"username\":\"janedoe\",\"password\":\"iloveyou\",\"employees\":[]}"));
    }
}

