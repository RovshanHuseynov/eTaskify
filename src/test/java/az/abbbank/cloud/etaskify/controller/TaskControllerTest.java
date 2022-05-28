package az.abbbank.cloud.etaskify.controller;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Employee;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.model.TaskStatusEnum;
import az.abbbank.cloud.etaskify.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TaskController.class})
@ExtendWith(SpringExtension.class)
public class TaskControllerTest {
    @Autowired private TaskController taskController;
    @MockBean private TaskService taskService;

    @Test
    public void testGetTaskById() throws Exception {
        // given
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 6, 1).atStartOfDay();
        Task task = Task.builder()
                .id(123L)
                .companyId(123L)
                .title("email automation")
                .description("automate all the emails from customer")
                .status(TaskStatusEnum.NEW)
                .employees(new ArrayList<Employee>())
                .deadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()))
                .build();

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
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 6, 20).atStartOfDay();
        Task task = Task.builder()
                .id(123L)
                .companyId(123L)
                .title("parse log")
                .description("parse and categorize all the logs into database")
                .status(TaskStatusEnum.NEW)
                .employees(new ArrayList<Employee>())
                .deadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()))
                .build();

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

