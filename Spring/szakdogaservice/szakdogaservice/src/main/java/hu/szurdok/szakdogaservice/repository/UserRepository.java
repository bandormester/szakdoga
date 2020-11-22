package hu.szurdok.szakdogaservice.repository;

import hu.szurdok.szakdogaservice.enitites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
}
