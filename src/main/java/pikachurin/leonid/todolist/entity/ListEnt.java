package pikachurin.leonid.todolist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.*;

@Entity(name = "List")
@Table(name = "lists")
public class ListEnt {
    @Getter
    @Id
    private UUID id;

    @Getter
    @JsonIgnore
    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskEnt> tasks;

    @Getter @Setter
    private String name;

    @Getter
    @CreationTimestamp
    private Timestamp createDate;

    @Getter
    @UpdateTimestamp
    private Timestamp modifyDate;

    public ListEnt()
    {
        id = UUID.randomUUID();
        name = "";
    }

}
