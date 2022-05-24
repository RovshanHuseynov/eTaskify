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
    @Autowired
    private TaskController taskController;

    @MockBean
    private TaskService taskService;

    @Test
    public void testGetTaskById() throws Exception {
        Task task = new Task();
        task.setEmployees(new ArrayList<Employee>());
        task.setStatus(TaskStatusEnum.NEW);
        task.setId(123L);
        task.setTitle("Dr");
        task.setCompanyId(123L);
        task.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        when(this.taskService.getTaskById(anyLong())).thenReturn(task);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/task/{id}", 123L);
        MockMvcBuilders.standaloneSetup(this.taskController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":123,\"companyId\":123,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\","
                                        + "\"deadline\":-14400000,\"status\":\"NEW\"}"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        when(this.taskService.getAllTasks()).thenReturn(new ArrayList<Task>());
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
        when(this.taskService.getAllTasks()).thenReturn(new ArrayList<Task>());
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
        when(this.taskService.getAllTasksByEmployeeId(anyLong())).thenReturn(new ArrayList<Task>());
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
        when(this.taskService.getAllTasksByEmployeeId(anyLong())).thenReturn(new ArrayList<Task>());
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
        Task task = new Task();
        task.setEmployees(new ArrayList<Employee>());
        task.setStatus(TaskStatusEnum.NEW);
        task.setId(123L);
        task.setTitle("Dr");
        task.setCompanyId(123L);
        task.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        task.setDeadline(Date.from(atStartOfDayResult.atZone(ZoneId.systemDefault()).toInstant()));
        when(this.taskService.addTask(anyLong(), (AddTaskRequestDTO) any())).thenReturn(task);

        AddTaskRequestDTO addTaskRequestDTO = new AddTaskRequestDTO();
        addTaskRequestDTO.setEmployees(new ArrayList<Employee>());
        addTaskRequestDTO.setTitle("Dr");
        addTaskRequestDTO.setDescription("The characteristics of someone or something");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
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
                                "{\"id\":123,\"companyId\":123,\"title\":\"Dr\",\"description\":\"The characteristics of someone or something\","
                                        + "\"deadline\":-14400000,\"status\":\"NEW\"}"));
    }
}

