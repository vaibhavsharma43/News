package com.vaibhav.news.Controller.Editor;

import com.vaibhav.news.Entity.News;
import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Service.NewsService;
import com.vaibhav.news.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Editor")
public class NewsController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            boolean isUserUpdated = userService.updateUser(user,username);
            if (isUserUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Editor updated successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Editor update failed");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-Editor")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            user.getRoles().add("EDITOR");

            boolean isUserCreated = userService.saveUser(user);
            if (isUserCreated) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Editor creation successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Editor creation failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the Editor");
        }
    }

    @PostMapping()
    public News createNews(@RequestBody News news ) {

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            return newsService.saveNews(news,userName);
        }catch (Exception e){
            throw e;
        }

    }





    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@PathVariable ObjectId id) {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean isDeleted = newsService.deleteNews(id,userName);
            if (isDeleted) {
                return ResponseEntity.ok("News deleted successfully");
            } else {
                return ResponseEntity.ok("News not deleted successfully");
            }
        }catch (Exception e){
            throw e;
        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateNews(@PathVariable ObjectId id, @RequestBody News
            news) {
        try {

            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            boolean isUpdated = newsService.updateNews(id, news,userName);
            if (isUpdated) {
                return ResponseEntity.ok("News updated successfully");
            } else {
                return ResponseEntity.ok("News not updated successfully");
            }
        }catch (Exception e){
            throw e;
        }
    }
}