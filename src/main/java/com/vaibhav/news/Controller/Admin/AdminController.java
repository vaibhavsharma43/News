package com.vaibhav.news.Controller.Admin;

import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/Admin")
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            boolean isUserUpdated = userService.updateUser(user,username);
            if (isUserUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("Admin updated successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin update failed");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/create-Admin")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            user.getRoles().addAll(List.of("ADMIN","USER","EDITOR"));


            boolean isUserCreated = userService.saveUser(user);
            if (isUserCreated) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Admin creation successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin creation failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the Admin");
        }
    }

    @GetMapping("admin")
    public ResponseEntity<User> getUsers() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            return ResponseEntity.ok(userService.getByUsername(username));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }


    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
           User c= userService.findByUsername(username);
            List<User> users = userService.getAll();
            if (users != null && !users.isEmpty()) {
                log.info("User who put the request: {} with userRole: {}", username, c.getRoles());

                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
