package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.TodoCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoCheckRepository extends JpaRepository<TodoCheck, Integer> { }
