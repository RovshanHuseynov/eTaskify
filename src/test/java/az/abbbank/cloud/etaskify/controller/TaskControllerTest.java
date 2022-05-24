package az.abbbank.cloud.etaskify.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.model.TaskStatusEnum;
import az.abbbank.cloud.etaskify.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TaskController.class})
@ExtendWith(SpringExtension.class)
public class TaskControllerTest {
    @Autowired private TaskController taskController;
    @MockBean private TaskService taskService;

    @Test
    public void testGetTaskById() throws Exception {
        // given
        Task task = new Task();
        task.setId(123L);
        task.setCompanyId(123L);
        task.setTitle("email automation");
        task.setDescription("automate all the emails from customer");
        task.setStatus(TaskStatusEnum.NEW);
        task.setEmployees(new ArrayList<Employee>());
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 6, 1).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));

        // when
        when(this.taskService.getTaskById(anyLong())).thenReturn(task);

        // then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"companyId\":123,\"title\":\"email automation\",\"description\":\"automate all the emails from customer\","
                                        + "\"deadline\":1654027200000,\"status\":\"NEW\"}"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        // when
        when(this.taskService.getAllTasks()).thenReturn(new ArrayList<Task>());

        // then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task");
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllTasks2() throws Exception {
        // when
        when(this.taskService.getAllTasks()).thenReturn(new ArrayList<Task>());

        // then
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/task");
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllTasksByEmployeeId() throws Exception {
        // when
        when(this.taskService.getAllTasksByEmployeeId(anyLong())).thenReturn(new ArrayList<Task>());

        // then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tasksofEmp/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testGetAllTasksByEmployeeId2() throws Exception {
        // when
        when(this.taskService.getAllTasksByEmployeeId(anyLong())).thenReturn(new ArrayList<Task>());

        // then
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/tasksofEmp/{id}", 123L);
        getResult.contentType("Not all who wander are lost");
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void testAddTask() throws Exception {
        // given
        Task task = new Task();
        task.setId(123L);
        task.setCompanyId(123L);
        task.setTitle("parse log");
        task.setDescription("parse and categorize all the logs into database");
        task.setStatus(TaskStatusEnum.NEW);
        task.setEmployees(new ArrayList<Employee>());
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 6, 20).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));

        // when
        when(this.taskService.addTask(anyLong(), (AddTaskRequestDTO) any())).thenReturn(task);

        // then
        AddTaskRequestDTO addTaskRequestDTO = new AddTaskRequestDTO();
        addTaskRequestDTO.setTitle("new title");
        addTaskRequestDTO.setDescription("new description");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        addTaskRequestDTO.setEmployees(new ArrayList<Employee>());
        addTaskRequestDTO.setDeadline(Date.from(atStartOfDayResult1.atZone(ZoneId.systemDefault()).toInstant()));
        String content = (new ObjectMapper()).writeValueAsString(addTaskRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/task/{id}", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"companyId\":123,\"title\":\"parse log\",\"description\":\"parse and categorize all the logs into database\","
                                        + "\"deadline\":1655668800000,\"status\":\"NEW\"}"));
    }
}

