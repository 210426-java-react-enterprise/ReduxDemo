package com.revature.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health")
public class RootController
{
    @GetMapping
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.accepted().body("Everything is Okay");
    }

}
