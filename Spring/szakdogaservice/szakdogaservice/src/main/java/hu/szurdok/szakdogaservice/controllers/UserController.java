package hu.szurdok.szakdogaservice.controllers;

import hu.szurdok.szakdogaservice.misc.ApiToken;
import hu.szurdok.szakdogaservice.misc.RegisterStatus;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @GetMapping(value = "/{userId}/pic",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getUserPic(@PathVariable(value = "userId") Integer userId){
        return userService.getPicture(userId);
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<RegisterStatus> register(
            @RequestBody(required = false) byte[] picture,
            @RequestParam String fullname,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Boolean hasPicture
    ){
        return userService.register(picture, fullname, username, email, password, hasPicture);
    }
}
