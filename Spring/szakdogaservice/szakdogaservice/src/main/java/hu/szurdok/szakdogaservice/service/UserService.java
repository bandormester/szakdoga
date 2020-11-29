package hu.szurdok.szakdogaservice.service;

import hu.szurdok.szakdogaservice.enitites.TodoGroup;
import hu.szurdok.szakdogaservice.misc.ApiToken;
import hu.szurdok.szakdogaservice.misc.RegisterStatus;
import hu.szurdok.szakdogaservice.enitites.User;
import hu.szurdok.szakdogaservice.repository.UserRepository;
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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
                    ImageIO.write(image, "JPG", new File("user-pics\\"+username+".jpg"));
                } catch (IOException e) {
                    return ResponseEntity.ok().body(new RegisterStatus(false, "Unable to upload image"));
                }

            return ResponseEntity.ok().body(new RegisterStatus(true, "Registration successful"));
    }

    public ResponseEntity<byte[]> getPicture(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        System.out.println("User has pic");
        try {
            if (user.isPresent()) {
                System.out.println("User has pic 2");
                ClassPathResource img = new ClassPathResource("user-pics/" + user.get().getUserName() + ".jpg");
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
