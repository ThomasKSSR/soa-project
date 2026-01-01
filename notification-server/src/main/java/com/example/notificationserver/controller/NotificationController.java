package com.example.notificationserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping("/user-registered")
    public ResponseEntity<String> userRegistered(@RequestBody Map<String, String> payload) {
        System.out.println("User registered: " + payload);
        return ResponseEntity.ok("Notification received");
    }
}
