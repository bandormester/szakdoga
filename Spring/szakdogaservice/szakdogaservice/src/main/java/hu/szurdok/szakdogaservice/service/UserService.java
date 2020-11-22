package hu.szurdok.szakdogaservice.service;

import hu.szurdok.szakdogaservice.misc.ApiToken;
import hu.szurdok.szakdogaservice.misc.RegisterStatus;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAllQueries(){
       return userRepository.findAll();
    }

    public ApiToken tryLogin(String username, String password) {
        User candidate = userRepository.findByUserName(username);
        if(candidate != null){
            if(candidate.getPassword().equals(password)) return new ApiToken(candidate.getId(), "api-token-key");
        }
        return new ApiToken(0, "Bad username");
    }

    public ResponseEntity<RegisterStatus> register(byte[] picture, String fullname, String username, String email, String password, Boolean hasPicture) {
            User newUser = new User(null, fullname, username, email, password, hasPicture);
            try {
                userRepository.save(newUser);
            } catch(DataIntegrityViolationException e) {
                return ResponseEntity.ok().body(new RegisterStatus(false, " Username already exists"));
            } catch (DataAccessException e) {
                System.out.println(e.toString());
                return ResponseEntity.ok().body(new RegisterStatus(false, "Unable to access database"));
            }

            if(hasPicture)
                try{
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(picture));
                    ImageIO.write(image, "JPG", new File("F:\\Git\\szakdoga\\Spring\\szakdogaservice\\pics\\"+username+".jpg"));
                } catch (IOException e) {
                    return ResponseEntity.ok().body(new RegisterStatus(false, "Unable to upload image"));
                }

            return ResponseEntity.ok().body(new RegisterStatus(true, "Registration successful"));
    }
}
