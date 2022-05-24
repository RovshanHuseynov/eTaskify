package az.abbbank.cloud.etaskify.controller;

import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.service.CompanyService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class CompanyControllerTest {

    private static MockMvc mockMvc;
    private static CompanyService companyService = mock(CompanyService.class);

    @BeforeAll
    static void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(new CompanyController(companyService)).build();
    }

    @Test
    void getCompanyById() throws Exception {
        Company company = Company.builder()
                .name("company")
                .build();
        when(companyService.getCompanyById(1))
                .thenReturn(company);
        mockMvc.perform(get("/company/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Disabled
    void getAllCompanies() {
    }

    @Test
    @Disabled
    void addCompany() {
    }

    @Test
    @Disabled
    void updateCompany() {
    }
}