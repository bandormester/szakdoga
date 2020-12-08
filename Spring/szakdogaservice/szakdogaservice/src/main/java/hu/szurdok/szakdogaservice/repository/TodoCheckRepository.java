package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.TodoCheck;
import hu.szurdok.szakdogaservice.enitites.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoCheckRepository extends JpaRepository<TodoCheck, Integer>{
    List<TodoCheck> findByTaskId(Integer taskId);
}
