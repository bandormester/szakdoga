package hu.szurdok.szakdogaservice.task;

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
    @Column(name = "has_assignee")
    private Boolean hasAssignee;

    @Basic
    @Column(name = "has_checklist")
    private Boolean hasChecklist;

    Task(TaskDao taskDao, Boolean hasAssignee, Boolean hasChecklist){
        id = taskDao.getId();
        ownerId = taskDao.getOwnerId();
        groupId = taskDao.getGroupId();
        label = taskDao.getLabel();
        importance = taskDao.getImportance();
        description = taskDao.getDescription();
        lat = taskDao.getLat();
        lon = taskDao.getLon();
        this.hasAssignee = hasAssignee;
        this.hasChecklist = hasChecklist;
    }
}
