package pikachurin.leonid.todolist.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEnt {
    @Getter
    @Id
    private UUID id;

    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "list_id")
    private ListEnt list;

    @Getter @Setter
    private String name;

    @Getter @Setter
    @CreationTimestamp
    private java.sql.Timestamp create_date;

    @Getter @Setter
    @CreationTimestamp
    private java.sql.Timestamp modify_date;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private Integer priority;

    @Getter @Setter
    private Boolean is_done;

    public TaskEnt()
    {
        id = UUID.randomUUID();
    }
}
