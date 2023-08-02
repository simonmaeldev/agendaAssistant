package com.tasktician.server.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tasktician.server.restservice.other.User;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "world") String name) {
        User user = new User();
        user.setName(name);
        return String.format("Hello %s!", user.getName());
    }

}