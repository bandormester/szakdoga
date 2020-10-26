package hu.szurdok.szakdogaservice.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "checks")
public class TodoCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "task_id")
    private Integer taskId;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "done")
    private Boolean done;
}
