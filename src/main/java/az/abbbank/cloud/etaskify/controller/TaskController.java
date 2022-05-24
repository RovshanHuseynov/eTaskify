package az.abbbank.cloud.etaskify.controller;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Task;
import az.abbbank.cloud.etaskify.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    @Autowired private TaskService taskService;

    @GetMapping("/task/{id}")
    public Task getTaskById(@PathVariable("id") long taskId){
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/task")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks();
    }

    @GetMapping("/tasksofEmp/{id}")
    public List<Task> getAllTasksByEmployeeId(@PathVariable("id") long employeeId){
        return taskService.getAllTasksByEmployeeId(employeeId);
    }

    @PostMapping("/task/{id}")
    public Task addTask(@PathVariable("id") long companyId,
                           @RequestBody AddTaskRequestDTO request){
        return taskService.addTask(companyId, request);
    }
}
