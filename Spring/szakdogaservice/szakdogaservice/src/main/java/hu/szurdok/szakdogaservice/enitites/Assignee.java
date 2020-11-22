package hu.szurdok.szakdogaservice.enitites;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "assignees")
public class Assignee implements Serializable {

    @Id
    @Basic
    @Column(name = "user_id")
    private int userId;

    @Id
    @Basic
    @Column(name = "task_id")
    private int taskId;
}
