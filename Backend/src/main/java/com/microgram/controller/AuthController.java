package com.microgram.controller;

import com.microgram.dto.JwtAuthResponse;
import com.microgram.dto.LoginRequest;
import com.microgram.dto.SignupRequest;
import com.microgram.model.AuthProvider;
import com.microgram.model.Users;
import com.microgram.repository.UserRepository;
import com.microgram.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager,
                         UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(),
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        return ResponseEntity.ok(new JwtAuthResponse(jwt, loginRequest.getUsernameOrEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                .badRequest()
                .body("Username is already taken");
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                .badRequest()
                .body("Email is already registered");
        }

        Users user = new Users();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setRole("USER");

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Logged out successfully");
    }
}