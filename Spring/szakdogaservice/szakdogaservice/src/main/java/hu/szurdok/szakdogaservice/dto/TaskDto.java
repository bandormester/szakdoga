package hu.szurdok.szakdogaservice.dto;

import hu.szurdok.szakdogaservice.enitites.Task;
import hu.szurdok.szakdogaservice.enitites.TodoCheck;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.misc.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskDto {
    private Integer id;

    private Integer ownerId;

    private Integer groupId;

    private String label;

    private Importance importance;

    private String description;

    private Double lat;

    private Double lon;

    private List<User> assignees;

    private List<TodoCheck> checklist;

    public TaskDto(Task taskDao){
        id = taskDao.getId();
        ownerId = taskDao.getOwnerId();
        groupId = taskDao.getGroupId();
        label = taskDao.getLabel();
        importance = taskDao.getImportance();
        description = taskDao.getDescription();
        lat = taskDao.getLat();
        lon = taskDao.getLon();
        this.assignees = new ArrayList<>();
        this.checklist = new ArrayList<>();
    }
}
