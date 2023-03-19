package com.example.recipemanagementSystem.controller;

import com.example.recipemanagementSystem.exception.ResourceNotFoundException;
import com.example.recipemanagementSystem.model.User;
import com.example.recipemanagementSystem.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @GetMapping("/find-all")
    private List<User> findAllUser(){
        return userRepository.findAll();
    }
    @GetMapping("/find-by-id/{id}")
    private User findUser(@PathVariable int id){
        User user= userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user not exist "+id));
        return user;
    }
    @PostMapping("/create-user")
    private ResponseEntity<String> createUser(@RequestBody User user){
        JSONObject jsonObject = new JSONObject(user);
        List<String> validationList = validateUser(jsonObject);
        if(validationList.isEmpty()){
            User user1 = setUser(jsonObject);
            userRepository.save(user1);
            return new ResponseEntity<>("User saved", HttpStatus.CREATED);
        }
        else{
            String[] answer = Arrays.copyOf(validationList.toArray(),validationList.size(),String[].class);
            return new ResponseEntity<>("Please pass these parameters"+Arrays.toString(answer),HttpStatus.BAD_REQUEST);
        }
    }

    private User setUser(JSONObject jsonObject) {
        User user = new User();
        if(jsonObject.has("firstName")){
            String fname = jsonObject.getString("firstName");
            user.setFirstName(fname);
        }
        if(jsonObject.has("lastName")){
            String lname= jsonObject.getString("lastName");
            user.setLastName(lname);
        }
        if(jsonObject.has("email")){
            String emai = jsonObject.getString("email");
            user.setEmail(emai);
        }
        return user;
    }

    private List<String> validateUser(JSONObject jsonObject) {
        List<String> errorList = new ArrayList<>();
        if(!jsonObject.has("firstName")){
            errorList.add("firstName");
        }
        if(!jsonObject.has("lastName")){
            errorList.add("lastName");
        }
        if(!jsonObject.has("email")){
            errorList.add("email");
        }
        return errorList;
    }
}
