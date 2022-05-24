package az.abbbank.cloud.etaskify.service.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Company;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.exception.InvalidEmployeeException;
import az.abbbank.cloud.etaskify.model.TaskStatusEnum;
import az.abbbank.cloud.etaskify.repository.TaskRepository;
import az.abbbank.cloud.etaskify.service.CompanyService;
import az.abbbank.cloud.etaskify.service.EmployeeService;
import az.abbbank.cloud.etaskify.util.EmailUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailUtil.class, TaskServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class TaskServiceImplTest {
    @MockBean private CompanyService companyService;
    @MockBean private EmailUtil emailUtil;
    @MockBean private EmployeeService employeeService;
    @MockBean private TaskRepository taskRepository;
    @Autowired private TaskServiceImpl taskServiceImpl;

    @Test
    public void testGetTaskById() {
        Task task = new Task();
        task.setEmployees(new ArrayList<Employee>());
        task.setStatus(TaskStatusEnum.NEW);
        task.setId(123L);
        task.setTitle("Dr");
        task.setCompanyId(123L);
        task.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        Optional<Task> ofResult = Optional.<Task>of(task);
        when(this.taskRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(task, this.taskServiceImpl.getTaskById(123L));
        verify(this.taskRepository).findById((Long) any());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    public void testGetTaskById2() {
        when(this.taskRepository.findById((Long) any())).thenReturn(Optional.<Task>empty());
        assertThrows(InvalidEmployeeException.class, () -> this.taskServiceImpl.getTaskById(123L));
        verify(this.taskRepository).findById((Long) any());
    }

    @Test
    public void testGetAllTasks() {
        ArrayList<Task> taskList = new ArrayList<Task>();
        when(this.taskRepository.findAll()).thenReturn(taskList);
        List<Task> actualAllTasks = this.taskServiceImpl.getAllTasks();
        assertSame(taskList, actualAllTasks);
        assertTrue(actualAllTasks.isEmpty());
        verify(this.taskRepository).findAll();
    }

    @Test
    public void testGetAllTasksByEmployeeId() {
        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");

        Employee employee = new Employee();
        employee.setEmail("jane.doe@example.org");
        employee.setPassword("iloveyou");
        employee.setId(123L);
        employee.setName("Name");
        employee.setCompany(company);
        employee.setSurname("Doe");
        ArrayList<Task> taskList = new ArrayList<Task>();
        employee.setTasks(taskList);
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);
        List<Task> actualAllTasksByEmployeeId = this.taskServiceImpl.getAllTasksByEmployeeId(123L);
        assertSame(taskList, actualAllTasksByEmployeeId);
        assertTrue(actualAllTasksByEmployeeId.isEmpty());
        verify(this.employeeService).getEmployeeById(anyLong());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    public void testAddTask() {
        Task task = new Task();
        task.setEmployees(new ArrayList<Employee>());
        task.setStatus(TaskStatusEnum.NEW);
        task.setId(123L);
        task.setTitle("Dr");
        task.setCompanyId(123L);
        task.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        when(this.taskRepository.save((Task) any())).thenReturn(task);
        doNothing().when(this.emailUtil).notifyEmployeesByEmail(anyLong(), (List<Employee>) any());

        Company company = new Company();
        company.setEmployees(new ArrayList<Employee>());
        company.setEmail("jane.doe@example.org");
        company.setPassword("iloveyou");
        company.setUsername("janedoe");
        company.setId(123L);
        company.setName("Name");
        company.setPhoneNumber("4105551212");
        company.setAddress("42 Main St");
        when(this.companyService.getCompanyById(anyLong())).thenReturn(company);
        assertSame(task, this.taskServiceImpl.addTask(123L, new AddTaskRequestDTO()));
        verify(this.taskRepository).save((Task) any());
        verify(this.emailUtil).notifyEmployeesByEmail(anyLong(), (List<Employee>) any());
        verify(this.companyService).getCompanyById(anyLong());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }
}

