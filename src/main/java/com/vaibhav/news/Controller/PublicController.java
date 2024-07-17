package com.vaibhav.news.Controller;

import com.vaibhav.news.Entity.News;
import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Service.NewsService;
import com.vaibhav.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            boolean isUserCreated = userService.saveUser(user);
            if (isUserCreated) {
                return ResponseEntity.status(HttpStatus.CREATED).body("User creation successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User creation failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the user");
        }
    }
    @GetMapping("allNews")
    public List<News> getAllNews() {

        try{


            return newsService.getAllNews();
        }catch (Exception e){
            throw e;
        }

    }
}
