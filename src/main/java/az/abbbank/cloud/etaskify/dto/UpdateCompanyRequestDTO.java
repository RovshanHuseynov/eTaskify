package az.abbbank.cloud.etaskify.dto;

import az.abbbank.cloud.etaskify.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyRequestDTO {
    private String name;
    private String phoneNumber;
    private String address;
    private String email;
    private String username;
    private String password;
    private List<Employee> employees;
}
