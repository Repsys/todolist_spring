package pikachurin.leonid.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

/**
 * JPA Сущность Задачи
 */
@Entity(name = "Task")
@Table(name = "tasks")
public class TaskEnt {
    @Getter
    @Id
    private final UUID id = UUID.randomUUID();

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private ListEnt list;

    @Getter @Setter
    private String name;

    @Getter
    @CreationTimestamp
    private java.sql.Timestamp createDate;

    @Getter
    @UpdateTimestamp
    private java.sql.Timestamp modifyDate;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Boolean isDone;

}
