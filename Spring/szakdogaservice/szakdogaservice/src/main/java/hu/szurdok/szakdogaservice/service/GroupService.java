package hu.szurdok.szakdogaservice.service;

import hu.szurdok.szakdogaservice.enitites.TodoGroup;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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

    public ResponseEntity<String> createGroup(byte[] picture, String groupName, Integer ownerId, String description, String joinCode, Boolean hasPicture) {
        TodoGroup group = new TodoGroup(null, groupName, ownerId, description, joinCode, hasPicture);

        try {
            groupRepository.save(group);
        } catch(DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( "Group name already exists");
        } catch (DataAccessException e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to access database");
        }

        if(hasPicture)
            try{
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(picture));
                ImageIO.write(image, "JPG", new File("F:\\Git\\szakdoga\\Spring\\szakdogaservice\\group-pics\\"+groupName+".jpg"));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to upload image");
            }

        return ResponseEntity.ok().body("Registration successful");
    }

    public ResponseEntity<byte[]> getPicture(Integer groupId) {
        Optional<TodoGroup> group = groupRepository.findById(groupId);
        System.out.println("Pictured");
        try {
            if (group.isPresent()) {
                System.out.println("Pictured");
                ClassPathResource img = new ClassPathResource("pics/" + group.get().getName() + ".jpg");
                byte[] bytes = StreamUtils.copyToByteArray(img.getInputStream());
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
