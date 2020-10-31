package pikachurin.leonid.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.*;

/**
 * JPA Сущность Списка задач
 */
@Entity(name = "List")
@Table(name = "lists")
public class ListEnt {
    @Getter
    @NotNull
    @Id
    private final UUID id = UUID.randomUUID();

    @Getter
    @JsonIgnore
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEnt> tasks;

    @Getter @Setter
    @NotBlank
    @Column(length = 128)
    private String name;

    @Getter
    @CreationTimestamp
    private Timestamp createDate;

    @Getter
    @UpdateTimestamp
    private Timestamp modifyDate;


}
