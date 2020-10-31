package pikachurin.leonid.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.UUID;

/**
 * JPA Сущность Задачи
 */
@Entity(name = "Task")
@Table(name = "tasks")
public class TaskEnt {
    @Getter
    @NotNull
    @Id
    private final UUID id = UUID.randomUUID();

    @Getter @Setter
    @JsonIgnore
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private ListEnt list;

    @Getter @Setter
    @NotBlank
    @Column(length = 128)
    private String name;

    @Getter
    @CreationTimestamp
    private java.sql.Timestamp createDate;

    @Getter
    @UpdateTimestamp
    private java.sql.Timestamp modifyDate;

    @Getter @Setter
    @NotNull
    @Column(length = 256)
    private String description;

    @Getter @Setter
    @NotNull
    private Integer priority;

    @Getter @Setter
    @NotNull
    private Boolean isDone;

}
