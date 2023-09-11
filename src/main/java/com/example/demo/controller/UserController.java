package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private SessionRepository sessionRepository;
    private Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private final UserServiceImp userServiceImp;
//    @Autowired
//    private final AuthenticationService authService;

    protected String userToken = "";

    @GetMapping("/")
    public String showHomePage(){
        return "index";
    }

    @GetMapping("/all")
    public String showUserList(Model model){
        List<User> listUsers = userServiceImp.listAllUser();
        model.addAttribute("listUsers", listUsers);
        log.info("Direct to user Maneger Page");
        return "user/UserManager";
    }

    @GetMapping("/add")
    public String getAddPage(Model model){
        model.addAttribute("user", new User());
        log.info("Direct to Add user Page");
        return "user/AddUserPage";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute User user){
        log.info("User Register");
        Optional<User> existed = userServiceImp.findByName(user.getName());

        if(existed.isEmpty()){
            userServiceImp.saveUser(user);
            log.info("Added User");
        } else {
            log.warn("User Existed");
        }

        return "redirect:/product/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userID") String id){
        Optional<User> existed = userServiceImp.findbyId(id);

        if(existed.isEmpty()){
            log.warn("user Non-Exist");
        } else {
            log.info("user Deleted");
            userServiceImp.deleteUserbyId(id);
        }
        return "redirect:/users/all";
    }

    @GetMapping("/update/{userid}")
    public String getUpdatePage(@PathVariable("userid") String userid, Model model){
        model.addAttribute("user", userServiceImp.findById(userid));
        log.info("Direct to Update product Page");
        return "user/UpdateUserPage";
    }

    @PostMapping("/update/{userid}")
    public String updateUser(@ModelAttribute User user, @PathVariable String userid){
        Optional<User> existed = userServiceImp.findbyId(userid);

        if(existed.isPresent()){
            existed.get().setName(user.getName());
            existed.get().setEmail(user.getEmail());
            existed.get().setPassword(user.getPassword());
            userServiceImp.updateUser(existed.get());
            log.info("Add user Successful");
        } else {
            log.info("Add user Fail");
        }
        return "redirect:/users/all";
    }

    @GetMapping("/login")
    public String getloginpage(Model model){
        model.addAttribute("user", new User());
        log.info("Direct to Login Page");
        return "main/LoginPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user){
        Optional<User> existed = userServiceImp.findByName(user.getName());
        if(existed.isPresent() && Objects.equals(existed.get().getPassword(), user.getPassword())){
            return "redirect:/product/";
        } else {
            return "Something wrong with your information";
        }
    }
}
