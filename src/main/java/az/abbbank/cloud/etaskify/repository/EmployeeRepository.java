package az.abbbank.cloud.etaskify.repository;

import az.abbbank.cloud.etaskify.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
