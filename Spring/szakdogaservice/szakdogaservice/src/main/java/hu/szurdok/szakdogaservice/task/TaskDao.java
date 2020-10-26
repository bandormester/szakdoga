package hu.szurdok.szakdogaservice.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TaskDao {
    private Integer id;

    private Integer ownerId;

    private Integer groupId;

    private String label;

    private Importance importance;

    private String description;

    private Double lat;

    private Double lon;

    private List<Integer> assignees;

    private List<TodoCheck> checkList;
}
