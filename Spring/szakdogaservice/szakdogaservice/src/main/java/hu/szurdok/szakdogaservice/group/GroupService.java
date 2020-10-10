package hu.szurdok.szakdogaservice.group;

import hu.szurdok.szakdogaservice.user.User;
import hu.szurdok.szakdogaservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;

    public List<Group> findAllQueries(){
        return groupRepository.findAll();
    }

    public List<Group> getMyGroups(Integer userId){
        return groupRepository.findByOwnerId(userId);
    }
}
