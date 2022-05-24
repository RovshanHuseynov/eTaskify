package az.abbbank.cloud.etaskify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeRequestDTO {
    private String name;
    private String surname;
    private String email;
}
