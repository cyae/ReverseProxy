package com.example.reverseproxy.Controller;

import com.example.reverseproxy.Model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public User getUser() {
        return new User("jack", 23);
    }
}
