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
        // given
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Task task = Task.builder()
                .id(123L)
                .title("Dr")
                .status(TaskStatusEnum.NEW)
                .companyId(123L)
                .description("The characteristics of someone or something")
                .deadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()))
                .employees(new ArrayList<Employee>())
                .build();

        Optional<Task> ofResult = Optional.<Task>of(task);

        // when
        when(this.taskRepository.findById((Long) any())).thenReturn(ofResult);

        // then
        assertSame(task, this.taskServiceImpl.getTaskById(123L));
        verify(this.taskRepository).findById((Long) any());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    public void testGetTaskById2() {
        // when
        when(this.taskRepository.findById((Long) any())).thenReturn(Optional.<Task>empty());

        // then
        assertThrows(InvalidEmployeeException.class, () -> this.taskServiceImpl.getTaskById(123L));
        verify(this.taskRepository).findById((Long) any());
    }

    @Test
    public void testGetAllTasks() {
        // given
        ArrayList<Task> taskList = new ArrayList<Task>();

        // when
        when(this.taskRepository.findAll()).thenReturn(taskList);
        List<Task> actualAllTasks = this.taskServiceImpl.getAllTasks();

        // then
        assertSame(taskList, actualAllTasks);
        assertTrue(actualAllTasks.isEmpty());
        verify(this.taskRepository).findAll();
    }

    @Test
    public void testGetAllTasksByEmployeeId() {
        // given
        Company company = Company.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .employees(new ArrayList<Employee>())
                .build();

        Employee employee = Employee.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .surname("Doe")
                .company(company)
                .build();

        ArrayList<Task> taskList = new ArrayList<Task>();
        employee.setTasks(taskList);

        // when
        when(this.employeeService.getEmployeeById(anyLong())).thenReturn(employee);
        List<Task> actualAllTasksByEmployeeId = this.taskServiceImpl.getAllTasksByEmployeeId(123L);

        // then
        assertSame(taskList, actualAllTasksByEmployeeId);
        assertTrue(actualAllTasksByEmployeeId.isEmpty());
        verify(this.employeeService).getEmployeeById(anyLong());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }

    @Test
    public void testAddTask() {
        // given
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Task task = Task.builder()
                .id(123L)
                .title("Dr")
                .status(TaskStatusEnum.NEW)
                .companyId(123L)
                .description("The characteristics of someone or something")
                .deadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()))
                .employees(new ArrayList<Employee>())
                .build();

        // when
        when(this.taskRepository.save((Task) any())).thenReturn(task);
        doNothing().when(this.emailUtil).notifyEmployeesByEmail(anyLong(), (List<Employee>) any());

        Company company = Company.builder()
                .id(123L)
                .name("Name")
                .email("jane.doe@example.org")
                .password("iloveyou")
                .username("janedoe")
                .phoneNumber("4105551212")
                .address("42 Main St")
                .employees(new ArrayList<Employee>())
                .build();

        when(this.companyService.getCompanyById(anyLong())).thenReturn(company);

        // then
        assertSame(task, this.taskServiceImpl.addTask(123L, new AddTaskRequestDTO()));
        verify(this.taskRepository).save((Task) any());
        verify(this.emailUtil).notifyEmployeesByEmail(anyLong(), (List<Employee>) any());
        verify(this.companyService).getCompanyById(anyLong());
        assertTrue(this.taskServiceImpl.getAllTasks().isEmpty());
    }
}

