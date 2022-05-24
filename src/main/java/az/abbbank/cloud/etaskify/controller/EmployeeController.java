package az.abbbank.cloud.etaskify.controller;

import az.abbbank.cloud.etaskify.dto.AddEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.dto.UpdateEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired private EmployeeService employeeService;

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable("id") long employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping("/employee/{id}")
    public Employee addEmployee(@PathVariable("id") long companyId,
                                   @RequestBody AddEmployeeRequestDTO request){
        return employeeService.addEmployee(companyId, request);
    }

    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody UpdateEmployeeRequestDTO request){
        return employeeService.updateEmployee(request);
    }
}
