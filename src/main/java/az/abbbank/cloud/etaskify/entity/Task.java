package az.abbbank.cloud.etaskify.entity;

import az.abbbank.cloud.etaskify.model.TaskStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "company_id", nullable = false)
    private long companyId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private Date deadline;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private TaskStatusEnum status;

    @ManyToMany(mappedBy = "tasks", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Employee> employees;
}