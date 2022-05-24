package az.abbbank.cloud.etaskify.service.impl;

import az.abbbank.cloud.etaskify.dto.AddEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.dto.UpdateEmployeeRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.model.ErrorMessagesEnum;
import az.abbbank.cloud.etaskify.repository.EmployeeRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.service.EmployeeService;
import az.abbbank.cloud.etaskify.util.GeneratorUtil;
import az.abbbank.cloud.etaskify.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private ValidationUtil validationUtil;
    @Autowired private CompanyService companyService;

    @Override
    public Employee getEmployeeById(long employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new InvalidEmployeeException(ErrorMessagesEnum.INVALID_EMPLOYEE.getMessage(employeeId)));
    }

    @Override
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(long companyId, AddEmployeeRequestDTO request){
        Company company = companyService.getCompanyById(companyId);

        Employee employee = Employee.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(GeneratorUtil.generatePassword(8))
                .company(company)
                .tasks(new ArrayList<>())
                .build();

        company.getEmployees().add(employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeRequestDTO request){
        Employee employee = getEmployeeById(request.getId());
        validationUtil.validatePassword(employee.getPassword());

        employee.setName(request.getName());
        employee.setSurname(request.getSurname());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());

        return employeeRepository.save(employee);
    }
}
