package com.example.userservice.controller;

import com.example.userservice.dto.UserLoginRequest;
import com.example.userservice.dto.UserRegisterRequest;
import com.example.userservice.entity.User;
import com.example.userservice.event.UserEventPublisher;
import com.example.userservice.event.UserKafkaProducer;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserEventPublisher publisher;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserKafkaProducer kafkaProducer;

    @PostMapping("/register")
    public String register(@RequestBody UserRegisterRequest req) {
        User user = new User();
        user.setUsername(req.username());
        user.setPassword(encoder.encode(req.password()));
        user.setRole("ROLE_USER");
        repo.save(user);

        publisher.userRegistered(user.getUsername());

        kafkaProducer.publishUserRegistered(req.username());

        return "User registered";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        return jwtUtil.generateToken(req.username());
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/whoami")
    public String whoAmI() {
        publisher.userRegistered("user1");

        kafkaProducer.publishUserRegistered("user1");
        return "Handled by port " + port;
    }





}