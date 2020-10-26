package hu.szurdok.szakdogaservice.task;

import hu.szurdok.szakdogaservice.ApiToken;
import hu.szurdok.szakdogaservice.RegisterStatus;
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
    public @ResponseBody ResponseEntity<RegisterStatus> createTask(
        @RequestBody(required = true) TaskDao task,
        @RequestParam Boolean hasAssignee,
        @RequestParam Boolean hasChecklist
    ){
        return taskService.createTask(task, hasAssignee, hasChecklist);
    }
}
