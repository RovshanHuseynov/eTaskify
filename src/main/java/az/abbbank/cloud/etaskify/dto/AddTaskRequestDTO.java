package az.abbbank.cloud.etaskify.dto;

import az.abbbank.cloud.etaskify.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTaskRequestDTO {
    private String title;
    private String description;
    private Date deadline;
    private List<Employee> employees;
}
