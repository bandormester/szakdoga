package hu.szurdok.szakdogaservice.controllers;

import hu.szurdok.szakdogaservice.misc.RegisterStatus;
import hu.szurdok.szakdogaservice.service.TaskService;
import hu.szurdok.szakdogaservice.dto.TaskBrowseDto;
import hu.szurdok.szakdogaservice.dto.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping()
    public ResponseEntity<RegisterStatus> createTask(
        @RequestBody(required = true) TaskDto task,
        @RequestParam Boolean hasAssignee,
        @RequestParam Boolean hasChecklist
    ){
        return taskService.createTask(task, hasAssignee, hasChecklist);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TaskBrowseDto>> getMyTasks(
            @PathVariable Integer userId,
            @RequestParam Integer groupId
    ){
        return taskService.getMyTasks(groupId, userId);
    }

    @GetMapping()
    public ResponseEntity<List<TaskBrowseDto>> getTasks(
            @RequestParam Integer groupId
    ){
        return taskService.getTasks(groupId);
    }

    @GetMapping("/{userId}/done")
    public ResponseEntity<List<TaskBrowseDto>> getDoneTasks(
            @PathVariable Integer userId,
            @RequestParam Integer groupId
    ){
        return taskService.getDoneTasks(groupId, userId);
    }

    @GetMapping("/id/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(
            @PathVariable Integer taskId
    ){
        return taskService.getTaskById(taskId);
    }

    @PutMapping("/check/{checkId}")
    public ResponseEntity<Void> checkItem(
            @PathVariable Integer checkId,
            @RequestParam Boolean isDone
    ){
        return taskService.checkItem(checkId, isDone);
    }

    @DeleteMapping("/id/{taskId}")
    public ResponseEntity<Void> finishTask(
            @PathVariable Integer taskId
    ){
        return taskService.finishTask(taskId);
    }
}
