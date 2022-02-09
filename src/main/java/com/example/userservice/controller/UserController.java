package com.example.userservice.controller;

import com.example.userservice.VO.Department;
import com.example.userservice.VO.ResponseObject;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;
    @PostMapping("/")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/{userID}")
    public ResponseObject getUserAndDepartmentByUserId(@PathVariable("userID") String userId){
        User user = userService.getUserByID(Long.valueOf(userId));
        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/"+String.valueOf(user.getDepartmentId()),
                Department.class);
        ResponseObject responseObject = new ResponseObject();
        responseObject.setUser(user);
        responseObject.setDepartment(department);
        return responseObject;
    }
}
