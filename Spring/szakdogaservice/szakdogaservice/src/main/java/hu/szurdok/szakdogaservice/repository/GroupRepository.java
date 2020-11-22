package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.TodoGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<TodoGroup, Integer> {
    List<TodoGroup> findByOwnerId(Integer ownerId);
}
