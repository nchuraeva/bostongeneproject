package com.example.secondtask.web.controllers;


import com.example.secondtask.entities.User;
import com.example.secondtask.exception.UserNotFoundException;
import com.example.secondtask.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private static final String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

    @GetMapping(value ="/users")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }


    @PostMapping(value = "/user/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestBody User user) {
       Pattern emailPattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        if(this.userService.findUserByEmail(user.getEmail()).isPresent()) {
            return "Error: this email already exists!";
        }
        if(!emailPattern.matcher(user.getEmail()).matches()) {
            return "Invalid email!";
        }
        Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
        if(!passwordPattern.matcher(user.getPassword()).matches()) {
            return "Invalid password!";
        }
       userService.add(user);
       return "User created!";
    }

    @GetMapping(value="/user/find/email")
    @ResponseStatus(HttpStatus.OK)
    public User findUserByEmail(@RequestParam Map<String,String> request) {
        String email = request.get("email");
        return this.userService.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email));
    }

    @DeleteMapping(value = "/user")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam Map<String,String> request){
        String email = request.get("email");
        this.validateUser(email);
        User user = this.userService.findUserByEmail(email).get();
        userService.deleteUser(user.getId());
    }

    private void validateUser(String email) {
        this.userService.findUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException(email));
    }

    private void validateUser(Long userId) {
        this.userService.findByUserId(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }


}
