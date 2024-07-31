package com.vaibhav.news.Controller.User;

import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserService userService;




    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            boolean isUserUpdated = userService.updateUser(user,username);
            if (isUserUpdated) {
                return ResponseEntity.status(HttpStatus.OK).body("User updated successful");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User update failed");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
          boolean delete=  userService.deleteByUserName(username);
          if (delete) {
              return ResponseEntity.status(HttpStatus.OK).body("User deleted successful");
          }else{
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
          }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



}
