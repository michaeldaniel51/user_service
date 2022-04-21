package com.dannycodes.user.controller;

import com.dannycodes.user.VO.ResponseTemplateVO;
import com.dannycodes.user.entity.User;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dannycodes.user.service.UserService;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;




         @PostMapping("/")
          public User saveUser (@RequestBody User user){
            log.info("inside saveUser of UserController");
            return userService.saveUser(user);
        }
            @GetMapping("/{id}")
            public ResponseTemplateVO getUserWithDepartment(@PathVariable("id") Long userId){
             log.info("Inside saveUser of UserController");
             return userService.getUserWithDepartment(userId);
            }



}
