package hu.szurdok.szakdogaservice.group;

import hu.szurdok.szakdogaservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping()
    public
    @ResponseBody List<TodoGroup> getGroups(){
        return groupService.findAllQueries();
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody ResponseEntity<List<TodoGroup>> getMyGroups(@PathVariable(value = "userId") Integer userId){
        return groupService.getMyGroups(userId);
    }

    @GetMapping("/{groupId}")
    public @ResponseBody ResponseEntity<TodoGroup> getGroup(@PathVariable(value = "groupId") Integer groupId){
        return groupService.getGroup(groupId);
    }

    @GetMapping("/{groupId}/members")
    public @ResponseBody ResponseEntity<List<User>> getMembers(@PathVariable(value = "groupId") Integer groupId){
        return groupService.getMembers(groupId);
    }

    @DeleteMapping("/{groupId}/members")
    public @ResponseBody ResponseEntity<String> removeMember(@PathVariable(value = "groupId") Integer groupId,
                                                             @RequestParam(value = "userId") Integer userId,
                                                             @RequestParam(value = "removedId") Integer removedId){
        return groupService.removeMember(groupId, userId, removedId);
    }
}
