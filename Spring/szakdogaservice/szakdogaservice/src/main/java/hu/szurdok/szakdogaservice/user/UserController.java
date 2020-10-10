package hu.szurdok.szakdogaservice.user;

import hu.szurdok.szakdogaservice.ApiToken;
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
    public @ResponseBody
    ApiToken tryLogin(
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
            @RequestParam String password,
            @RequestParam Boolean hasPicture
    ){
        return userService.register(picture, fullname, username, email, password, hasPicture);
    }
}
