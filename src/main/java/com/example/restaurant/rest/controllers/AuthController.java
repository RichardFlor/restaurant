package com.example.restaurant.rest.controllers;

import com.example.restaurant.dtos.user.input.LoginInputDTO;
import com.example.restaurant.dtos.user.output.LoginOutputDTO;
import com.example.restaurant.entities.user.User;
import com.example.restaurant.dtos.user.input.RegisterRequestDTO;
import com.example.restaurant.dtos.user.input.ResponseDTO;
import com.example.restaurant.security.services.JwtTokenService;
import com.example.restaurant.repositories.UserRepository;
import com.example.restaurant.usecases.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final LoginUseCase loginUseCase;

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody LoginRequestDTO body){
//        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
//        if(passwordEncoder.matches(body.password(), user.getPassword())) {
//            String token = this.jwtTokenService.generateToken(user);
//            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
//        }
//        return ResponseEntity.badRequest().build();
//    }

    @PostMapping("/login")
    public LoginOutputDTO login(@RequestBody @Valid LoginInputDTO request) {
        return loginUseCase.execute(request);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.jwtTokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
