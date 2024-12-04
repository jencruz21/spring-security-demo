package com.jencruz.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class GreetingController {

    @GetMapping(value = "greet")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello, World");
    }

    @GetMapping
    public ResponseEntity<String> mainPage() {
        return ResponseEntity.ok("main page");
    }
}
