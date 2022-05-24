package az.abbbank.cloud.etaskify.service.impl;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.model.ErrorMessagesEnum;
import az.abbbank.cloud.etaskify.model.TaskStatusEnum;
import az.abbbank.cloud.etaskify.repository.TaskRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.service.EmployeeService;
import az.abbbank.cloud.etaskify.service.TaskService;
import az.abbbank.cloud.etaskify.util.EmailUtil;
import az.abbbank.cloud.etaskify.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired private TaskRepository taskRepository;
    @Autowired private CompanyService companyService;
    @Autowired private EmployeeService employeeService;
    @Autowired private EmailUtil emailUtil;

    @Override
    public Task getTaskById(long taskId){
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new InvalidEmployeeException(ErrorMessagesEnum.INVALID_TASK.getMessage(taskId)));
    }

    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getAllTasksByEmployeeId(long employeeId){
        Employee employee = employeeService.getEmployeeById(employeeId);
        return employee.getTasks();
    }

    @Override
    public Task addTask(long companyId, AddTaskRequestDTO request){
        companyService.getCompanyById(companyId);

        Task task = Task.builder()
                .companyId(companyId)
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .status(TaskStatusEnum.NEW)
                .employees(request.getEmployees())
                .build();

        emailUtil.notifyEmployeesByEmail(task.getId(), task.getEmployees());
        return taskRepository.save(task);
    }
}