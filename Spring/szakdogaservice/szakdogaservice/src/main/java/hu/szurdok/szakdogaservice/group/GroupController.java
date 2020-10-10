package hu.szurdok.szakdogaservice.group;

import hu.szurdok.szakdogaservice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody List<Group> getGroups(){
        return groupService.findAllQueries();
    }

    @GetMapping("/user/{userId}")
    public List<Group> getMyGroups(@PathVariable(value = "userId") Integer userId){
        return groupService.getMyGroups(userId);
    }
}
