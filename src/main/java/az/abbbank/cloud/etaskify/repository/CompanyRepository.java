package az.abbbank.cloud.etaskify.repository;

import az.abbbank.cloud.etaskify.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}