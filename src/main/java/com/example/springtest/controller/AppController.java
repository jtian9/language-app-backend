package com.example.springtest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AppController {

    @GetMapping("/greet")
    public String greet() {
        return "get";
    }

    @PostMapping("/generate")
    public String generate() {
        return "test generate site";
    }

}
