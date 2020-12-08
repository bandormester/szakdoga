package hu.szurdok.szakdogaservice.dto;

import hu.szurdok.szakdogaservice.enitites.Task;
import hu.szurdok.szakdogaservice.misc.Importance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskBrowseDto {
    private Integer id;

    private Integer ownerId;

    private Integer groupId;

    private String label;

    private Importance importance;

    private Boolean hasDescription;

    private Boolean hasPlace;

    private Boolean hasAssignees;

    private Boolean hasChecklist;

    public TaskBrowseDto(Task task){
        id = task.getId();
        ownerId = task.getOwnerId();
        groupId = task.getGroupId();
        label = task.getLabel();
        importance = task.getImportance();
        hasDescription = task.getDescription() != null && !task.getDescription().isEmpty();
        hasPlace = task.getLat() != null && task.getLon() != null;
        hasAssignees = task.getHasAssignee();
        hasChecklist = task.getHasChecklist();
    }
}
