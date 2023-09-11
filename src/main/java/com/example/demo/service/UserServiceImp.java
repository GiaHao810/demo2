package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp {
    @Autowired
    private UserService userService;

    public List<User> listAllUser(){
        return userService.findAll();
    }

    public void saveUser(User user){
        userService.save(user);
    }

    public void deleteUser(User user){
        userService.delete(user);
    }

    public void deleteUserbyId(String id){
        userService.deleteById(id);
    }

    public Optional<User> findbyId(String id){
        return userService.findById(id);
    }

    public User findById(String id){
        return userService.findById(id).get();
    }

    public void updateUser(User updateUser){ userService.save(updateUser);}

    public Optional<User> findByName(String name){
        return userService.findByName(name);
    }
}
