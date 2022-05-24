package az.abbbank.cloud.etaskify.repository;

import az.abbbank.cloud.etaskify.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
