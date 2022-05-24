package az.abbbank.cloud.etaskify.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddCompanyRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.service.CompanyService;
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
    @Autowired private CompanyController companyController;
    @MockBean private CompanyService companyService;

    @Test
    public void testGetCompanyById() throws Exception {
        // given
        Company company = new Company();
        company.setId(123L);
        company.setName("ABB");
        company.setEmail("system@abb-bank.az");
        company.setPassword("abb123");
        company.setUsername("adminabb");
        company.setPhoneNumber("937");
        company.setAddress("Nizami street");
        company.setEmployees(new ArrayList<Employee>());

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
                                "{\"id\":123,\"name\":\"ABB\",\"phoneNumber\":\"937\",\"address\":\"Nizami street\",\"email\":\"system@abb-bank.az\"" +
                                        ",\"username\":\"adminabb\",\"password\":\"abb123\",\"employees\":[]}"));
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        // when
        when(this.companyService.getAllCompanies()).thenReturn(new ArrayList<Company>());

        // then
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
        // when
        when(this.companyService.getAllCompanies()).thenReturn(new ArrayList<Company>());

        // then
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
        // given
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setId(123L);
        company.setName("cybernet");
        company.setEmail("system@cybernet.az");
        company.setPassword("cybernet123");
        company.setUsername("admincybernet");
        company.setPhoneNumber("0704005567");
        company.setAddress("xetai street");

        // when
        when(this.companyService.addCompany((AddCompanyRequestDTO) any())).thenReturn(company);

        // then
        AddCompanyRequestDTO addCompanyRequestDTO = new AddCompanyRequestDTO();
        addCompanyRequestDTO.setEmployees(new ArrayList<Employee>());
        addCompanyRequestDTO.setEmail("new email");
        addCompanyRequestDTO.setName("new name");
        addCompanyRequestDTO.setPhoneNumber("new number");
        addCompanyRequestDTO.setAddress("new street");
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
                                "{\"id\":123,\"name\":\"cybernet\",\"phoneNumber\":\"0704005567\",\"address\":\"xetai street\",\"email\":\"system@cybernet.az\"," +
                                        "\"username\":\"admincybernet\",\"password\":\"cybernet123\",\"employees\":[]}"));
    }

    @Test
    public void testUpdateCompany() throws Exception {
        // given
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setId(123L);
        company.setName("azericard");
        company.setEmail("system@azericard.com");
        company.setPassword("azericard123");
        company.setUsername("adminazericard");
        company.setPhoneNumber("0124563464");
        company.setAddress("bulbul street");

        // when
        when(this.companyService.updateCompany((Company) any())).thenReturn(company);

        // then
        Company company1 = new Company();
        company1.setEmployees(new ArrayList<Employee>());
        company1.setId(123L);
        company1.setName("new name");
        company1.setEmail("new email");
        company1.setPassword("new password");
        company1.setUsername("new username");
        company1.setPhoneNumber("new number");
        company1.setAddress("new street name");
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
                                "{\"id\":123,\"name\":\"azericard\",\"phoneNumber\":\"0124563464\",\"address\":\"bulbul street\",\"email\":\"system@azericard.com\"" +
                                        ",\"username\":\"adminazericard\",\"password\":\"azericard123\",\"employees\":[]}"));
    }
}

