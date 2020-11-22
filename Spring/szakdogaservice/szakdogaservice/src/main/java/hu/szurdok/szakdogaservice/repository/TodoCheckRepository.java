package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.TodoCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCheckRepository extends JpaRepository<TodoCheck, Integer> {
  // @Query("UPDATE TodoCheck t SET t.isDone = :isDone WHERE u.id = :id")
  // void updateCheckDone(@Param(value = "id") Integer id, @Param(value = "isDone") Integer isDone);
}
