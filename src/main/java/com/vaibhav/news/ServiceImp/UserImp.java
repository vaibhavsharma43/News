package com.vaibhav.news.ServiceImp;

import com.vaibhav.news.Entity.User;
import com.vaibhav.news.Repo.UserRepository;
import com.vaibhav.news.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean saveUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveExistUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User getByUsername(String userName) {

        try {

            return userRepository.findByUserName(userName);


        } catch (Exception e) {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public boolean deleteByUserName(String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            if (user != null) {
                userRepository.deleteByUserName(userName);
                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUserName(username);


        } catch (Exception e) {
            throw new RuntimeException("User Not Found");
        }
    }

    @Override
    public boolean updateUser(User user, String userName) {
        try {
            User userInDb = userService.findByUsername(userName);


            if (userInDb != null) {
                userInDb.setUserName(user.getUserName());
                userInDb.setRoles(user.getRoles());
                userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(userInDb);
                return true;
            } else {
                return false;
            }


        } catch (Exception e) {

            throw new RuntimeException("Exception occurred while updating user");
        }
    }
}
