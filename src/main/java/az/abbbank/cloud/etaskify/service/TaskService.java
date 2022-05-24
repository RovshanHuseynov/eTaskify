package az.abbbank.cloud.etaskify.service;

import az.abbbank.cloud.etaskify.dto.AddTaskRequestDTO;
import az.abbbank.cloud.etaskify.entity.Task;

import java.util.List;

public interface TaskService {
    Task getTaskById(long taskId);

    List<Task> getAllTasks();

    List<Task> getAllTasksByEmployeeId(long employeeId);

    Task addTask(long companyId, AddTaskRequestDTO request);
}
