package hu.szurdok.szakdogaservice.task;

import hu.szurdok.szakdogaservice.ApiToken;
import hu.szurdok.szakdogaservice.RegisterStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Transactional
    public ResponseEntity<RegisterStatus> createTask(TaskDao taskDao, Boolean hasAssignee, Boolean hasChecklist) {

        Task task = new Task(taskDao, hasAssignee, hasChecklist);

        taskRepository.save(task);

        return ResponseEntity.ok().body(new RegisterStatus(true, "Registration successful"));
    }

}
