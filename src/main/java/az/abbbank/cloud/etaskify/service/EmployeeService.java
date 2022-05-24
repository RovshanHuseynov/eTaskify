package az.abbbank.cloud.etaskify.service;

import az.abbbank.cloud.etaskify.dto.AddEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.dto.UpdateEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getEmployeeById(long employeeId);

    List<Employee> getAllEmployees();

    Employee addEmployee(long companyId, AddEmployeeRequestDTO request);

    Employee updateEmployee(UpdateEmployeeRequestDTO request);
}