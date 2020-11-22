package hu.szurdok.szakdogaservice.enitites;

import hu.szurdok.szakdogaservice.misc.Importance;
import hu.szurdok.szakdogaservice.dto.TaskDto;
import hu.szurdok.szakdogaservice.misc.TaskState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "owner_id")
    private Integer ownerId;

    @Basic
    @Column(name = "group_id")
    private Integer groupId;

    @Basic
    @Column(name = "label")
    private String label;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "importance")
    private Importance importance;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "lat")
    private Double lat;

    @Basic
    @Column(name = "lon")
    private Double lon;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private TaskState taskState;

    @Basic
    @Column(name = "has_assignee")
    private Boolean hasAssignee;

    @Basic
    @Column(name = "has_checklist")
    private Boolean hasChecklist;

    public Task(TaskDto taskDto, Boolean hasAssignee, Boolean hasChecklist){
        id = taskDto.getId();
        ownerId = taskDto.getOwnerId();
        groupId = taskDto.getGroupId();
        label = taskDto.getLabel();
        importance = taskDto.getImportance();
        description = taskDto.getDescription();
        lat = taskDto.getLat();
        lon = taskDto.getLon();
        this.hasAssignee = hasAssignee;
        this.hasChecklist = hasChecklist;
    }
}
