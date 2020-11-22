package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByGroupId(Integer groupId);
    List<Task> findByGroupIdAndOwnerId(Integer groupId, Integer ownerId);
}
