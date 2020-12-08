package hu.szurdok.szakdogaservice.controllers;

import hu.szurdok.szakdogaservice.enitites.TodoGroup;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping()
    public ResponseEntity<String> createGroup(
            @RequestBody(required = false) byte[] picture,
            @RequestParam String groupName,
            @RequestParam Integer ownerId,
            @RequestParam String description,
            @RequestParam String joinCode,
            @RequestParam Boolean hasPicture
    ){
        return groupService.createGroup(picture, groupName, ownerId, description, joinCode, hasPicture);
    }

    @GetMapping(value = "/{groupId}/pic",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getGroupPic(@PathVariable(value = "groupId") Integer groupId){
        return groupService.getPicture(groupId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoGroup>> getMyGroups(@PathVariable(value = "userId") Integer userId){
        return groupService.getMyGroups(userId);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<TodoGroup> getGroup(@PathVariable(value = "groupId") Integer groupId){
        return groupService.getGroup(groupId);
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<User>> getMembers(@PathVariable(value = "groupId") Integer groupId){
        return groupService.getMembers(groupId);
    }

    @DeleteMapping("/{groupId}/members")
    public ResponseEntity<String> removeMember(@PathVariable(value = "groupId") Integer groupId,
                                                             @RequestParam(value = "userId") Integer userId,
                                                             @RequestParam(value = "removedId") Integer removedId){
        return groupService.removeMember(groupId, userId, removedId);
    }

    @PutMapping()
    public ResponseEntity<Void> joinGroup(@RequestParam(value = "code") String code,
                          @RequestParam(value = "userId") Integer userId){
        return groupService.joinGroup(code, userId);
    }
}
