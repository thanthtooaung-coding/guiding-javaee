package com.springboot.productmanagement.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class WelcomeController {
    @GetMapping
    public String welcome(){
        return "Hello Spring Boot";
    }

    @GetMapping("/hello")
    public String hello(
            @RequestParam(required=false) String name
    ){
        if (name != null && !name.isEmpty())
            return "Hello " + name;
        return "Hello Page 2";
    }

    @GetMapping("/hello/{name}")
    public String helloTwo(
            @PathVariable String name
    ){
        return "Hello " + name;
    }
}
