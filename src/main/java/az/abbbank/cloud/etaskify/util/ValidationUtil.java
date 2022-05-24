package az.abbbank.cloud.etaskify.util;

import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.exception.InvalidCompanyException;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.exception.InvalidPasswordException;
import az.abbbank.cloud.etaskify.exception.InvalidTaskException;

import java.util.Optional;

public interface ValidationUtil {
    void validatePassword(String password) throws InvalidPasswordException;

    void validateCompany(Optional<Company> company) throws InvalidCompanyException;

    void validateEmployee(Optional<Employee> employee) throws InvalidEmployeeException;

    void validateTask(Optional<Task> task) throws InvalidTaskException;
}
