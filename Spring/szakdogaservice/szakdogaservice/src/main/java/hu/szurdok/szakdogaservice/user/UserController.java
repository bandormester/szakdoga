package hu.szurdok.szakdogaservice.user;

import hu.szurdok.szakdogaservice.RegisterStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public @ResponseBody List<User> getUser(){
        return userService.findAllQueries();
    }

    @GetMapping("/login")
    public @ResponseBody Integer tryLogin(
            @RequestParam String username,
            @RequestParam String password
    ){
        return userService.tryLogin(username, password);
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<RegisterStatus> register(
            @RequestBody byte[] picture,
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ){
        return userService.register(picture, fullname, username, email, password);
    }

    @PostMapping("/register/nopic")
    public @ResponseBody ResponseEntity<RegisterStatus> registerNoPic(
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ){
        return userService.registerNoPic(fullname, username, email, password);
    }
}
