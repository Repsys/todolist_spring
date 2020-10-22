package pikachurin.leonid.todolist.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "lists")
public class ListEnt {
    @Getter
    @Id
    private UUID id;

    @Getter @Setter
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEnt> tasks;

    @Getter @Setter
    private String name;

    @Getter @Setter
    @CreationTimestamp
    private java.sql.Timestamp create_date;

    @Getter @Setter
    @CreationTimestamp
    private java.sql.Timestamp modify_date;

    public ListEnt()
    {
        id = UUID.randomUUID();
        name = "";
    }
}
