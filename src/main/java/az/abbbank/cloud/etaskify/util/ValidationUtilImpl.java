package az.abbbank.cloud.etaskify.util;

import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.exception.InvalidCompanyException;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.exception.InvalidPasswordException;
import az.abbbank.cloud.etaskify.exception.InvalidTaskException;
import az.abbbank.cloud.etaskify.model.ErrorMessagesEnum;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationUtilImpl implements ValidationUtil {
    @Override
    public void validatePassword(String password){
        if(password == null || password.length() < 6 || !isAlphaNumericFiscalId(password)){
            throw new InvalidPasswordException(ErrorMessagesEnum.INVALID_PASSWORD.getMessage());
        }
    }

    private boolean isAlphaNumericFiscalId(String fiscalId){
        final String regex = "^[0-9]*[a-zA-Z][a-zA-Z0-9]*$";
        return fiscalId.matches(regex);
    }

    @Override
    public void validateCompany(Optional<Company> company){
        if(!company.isPresent()){
            throw new InvalidCompanyException(ErrorMessagesEnum.INVALID_COMPANY.getMessage());
        }
    }

    @Override
    public void validateEmployee(Optional<Employee> employee){
        if(!employee.isPresent()){
            throw new InvalidEmployeeException(ErrorMessagesEnum.INVALID_EMPLOYEE.getMessage());
        }
    }

    @Override
    public void validateTask(Optional<Task> task){
        if(!task.isPresent()){
            throw new InvalidTaskException(ErrorMessagesEnum.INVALID_TASK.getMessage());
        }
    }
}
