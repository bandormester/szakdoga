package hu.szurdok.szakdogaservice.service;

import hu.szurdok.szakdogaservice.misc.RegisterStatus;
import hu.szurdok.szakdogaservice.dto.TaskBrowseDto;
import hu.szurdok.szakdogaservice.dto.TaskDto;
import hu.szurdok.szakdogaservice.enitites.Task;
import hu.szurdok.szakdogaservice.enitites.TodoCheck;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.misc.TaskState;
import hu.szurdok.szakdogaservice.repository.TaskRepository;
import hu.szurdok.szakdogaservice.repository.TodoCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TodoCheckRepository todoCheckRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public ResponseEntity<RegisterStatus> createTask(TaskDto taskDto, Boolean hasAssignee, Boolean hasChecklist) {
        Task task = new Task(taskDto, hasAssignee, hasChecklist);
        task.setTaskState(TaskState.OPEN);

        Task savedTask = taskRepository.save(task);

        if(hasChecklist){
            for(TodoCheck tc : taskDto.getChecklist()){
                tc.setTaskId(savedTask.getId());
            }
            todoCheckRepository.saveAll(taskDto.getChecklist());
        }
        if(hasAssignee)
            for(User u : taskDto.getAssignees()){
            em.createNativeQuery("INSERT INTO assignees (user_id, task_id) VALUES (?, ?)")
                    .setParameter(1, u.getId())
                    .setParameter(2, savedTask.getId())
                    .executeUpdate();
            }

        return ResponseEntity.ok().body(new RegisterStatus(true, "Registration successful"));
    }

    public ResponseEntity<List<TaskBrowseDto>> getMyTasks(Integer groupId, Integer userId){

        List<Integer> taskIds = em.createQuery("SELECT a.taskId FROM Assignee a WHERE a.userId = :id", Integer.class)
                .setParameter("id",userId).getResultList();

        List<Task> groupTasks = taskRepository.findByGroupId(groupId);
        List<TaskBrowseDto> tasks = new ArrayList<>();
        List<TaskBrowseDto> ownedTasks = new ArrayList<>();

        for(Task task : groupTasks){
            if(task.getTaskState() == TaskState.OPEN){
                if(taskIds.contains(task.getId())){
                    tasks.add(new TaskBrowseDto(task));
                }else if(task.getOwnerId().equals(userId)){
                    ownedTasks.add(new TaskBrowseDto(task));
                }
            }
        }
        tasks.addAll(ownedTasks);

        return ResponseEntity.ok(tasks);
    }

    public ResponseEntity<List<TaskBrowseDto>> getDoneTasks(Integer groupId, Integer userId){

        List<Task> ownedTasks = taskRepository.findByGroupIdAndOwnerId(groupId, userId);
        List<TaskBrowseDto> savedTasks = new ArrayList<>();
        List<TaskBrowseDto> tasks = new ArrayList<>();
        
        for(Task task : ownedTasks){
            switch (task.getTaskState()){
                case DONE:
                    tasks.add(new TaskBrowseDto(task));
                    break;
                case SAVED:
                    savedTasks.add(new TaskBrowseDto(task));
                    break;
                default:
                    break;
            }
        }

        tasks.addAll(savedTasks);

        return ResponseEntity.ok(tasks);
    }

    public ResponseEntity<List<TaskBrowseDto>> getTasks(Integer groupId){

        List<Task> tasks = taskRepository.findByGroupId(groupId);
        List<TaskBrowseDto> result = new ArrayList<>();

        for(Task task : tasks){
            if(task.getTaskState() == TaskState.OPEN){
                TaskBrowseDto dto = new TaskBrowseDto(task);
                result.add(dto);
            }
        }

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<TaskDto> getTaskById(Integer taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            TaskDto dto = new TaskDto(task);

            if(task.getHasAssignee()){
                List<User> assignees = em.createQuery("SELECT u FROM User u WHERE u.id IN (SELECT a.userId FROM Assignee a WHERE a.taskId = :id)", User.class)
                        .setParameter("id", task.getId()).getResultList();
                dto.setAssignees(assignees);
            }
            if(task.getHasChecklist()){
                List<TodoCheck> checks = todoCheckRepository.findByTaskId(taskId);
                dto.setChecklist(checks);
            }

            return ResponseEntity.ok(dto);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public ResponseEntity<Void> checkItem(Integer checkId, Boolean isDone) {
        Optional<TodoCheck> check = todoCheckRepository.findById(checkId);

        if(check.isPresent()){
            check.get().setDone(isDone);
            todoCheckRepository.save(check.get());
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    public ResponseEntity<Void> finishTask(Integer taskId) {
        Optional<Task> task = taskRepository.findById(taskId);

        if(task.isPresent()){
            taskRepository.deleteById(taskId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
