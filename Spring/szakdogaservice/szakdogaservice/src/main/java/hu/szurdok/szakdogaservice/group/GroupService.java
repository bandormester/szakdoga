package hu.szurdok.szakdogaservice.group;

import hu.szurdok.szakdogaservice.user.User;
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
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    @PersistenceContext
    EntityManager em;

    public List<TodoGroup> findAllQueries(){
        return groupRepository.findAll();
    }

    public ResponseEntity<List<TodoGroup>> getMyGroups(Integer userId){
        System.out.println("get groups lefutott");
        List<TodoGroup> list = groupRepository.findByOwnerId(userId);
        System.out.println(list.size());
        return ResponseEntity.ok().body(list);
    }

    public ResponseEntity<TodoGroup> getGroup(Integer groupId) {
        Optional<TodoGroup> group = groupRepository.findById(groupId);
        if(group.isPresent()){
            return ResponseEntity.ok().body(group.get());
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public ResponseEntity<List<User>> getMembers(Integer groupId) {
        List<User> members = em.createQuery("SELECT u FROM User u WHERE u.id IN (SELECT m.userId FROM Member m WHERE m.groupId = :id)", User.class)
                .setParameter("id",groupId).getResultList();

        return ResponseEntity.ok().body(members);
    }

    @Transactional
    public ResponseEntity<String> removeMember(Integer groupId, Integer userId, Integer removeId){
        Optional<TodoGroup> group = groupRepository.findById(groupId);

        if(group.isPresent()){
            if(group.get().getOwnerId().equals(userId)){
                int count = em.createQuery("DELETE FROM Member m WHERE m.groupId = :groupId AND m.userId = :removeId")
                        .setParameter("groupId", groupId)
                        .setParameter("removeId", removeId)
                        .executeUpdate();
                if(count == 1){
                    return ResponseEntity.ok().body("Successfully removed member");
                }
                else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Member not found");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only group owner can remove");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group not found");
        }
    }
}
