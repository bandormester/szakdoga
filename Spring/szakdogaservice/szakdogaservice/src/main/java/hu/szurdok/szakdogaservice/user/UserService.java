package hu.szurdok.szakdogaservice.user;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    public List<User> findAllQueries(){
       return userRepository.findAll();
    }

    public Integer tryLogin(String username, String password) {
        User candidate = userRepository.findByUserName(username);
        if(candidate != null){
            if(candidate.getPassword().equals(password)) return candidate.getId();
        }
        return 0;
    }

    public ResponseEntity<String> register(byte[] picture, String fullname, String username, String email, String password) {
            User newUser = new User(null, fullname, username, email, password, false);
            try {
                userRepository.save(newUser);
            }catch(DataIntegrityViolationException e) {
                System.out.println("Integrity");
                return new ResponseEntity<String>("Username already exists", new HttpHeaders(),  HttpStatus.CONFLICT);
                //return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
            }
            catch (DataAccessException e){
                System.out.println(e.toString());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Unable to connect to database");
            }

            if(picture != null){
                try{
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(picture));
                    ImageIO.write(image, "JPG", new File("F:\\Git\\szakdoga\\Spring\\szakdogaservice\\pics\\"+username+".jpg"));
                } catch (IOException e) {
                    System.out.println("Upload");
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to upload image");
                }
            }

            System.out.println("Success");
            return new ResponseEntity<String>("Registration successful", new HttpHeaders(),  HttpStatus.OK);
            //return ResponseEntity.ok().body("Registration successful");
    }
}
