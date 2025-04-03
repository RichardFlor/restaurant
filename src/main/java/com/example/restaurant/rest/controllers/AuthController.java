package com.example.restaurant.rest.controllers;

import com.example.restaurant.dtos.auth.input.LoginInputDTO;
import com.example.restaurant.dtos.auth.output.LoginOutputDTO;
import com.example.restaurant.rest.specs.AuthControllerSpecs;
import com.example.restaurant.usecases.auth.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpecs {
    private final LoginUseCase loginUseCase;

    @PostMapping("/login")
    public LoginOutputDTO login(@RequestBody @Valid LoginInputDTO request) {
        return loginUseCase.execute(request);
    }


}