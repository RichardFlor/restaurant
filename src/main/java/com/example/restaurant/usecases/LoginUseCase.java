package com.example.restaurant.usecases;

import com.example.restaurant.dtos.user.input.LoginInputDTO;
import com.example.restaurant.dtos.user.output.LoginOutputDTO;
import com.example.restaurant.security.dto.UserDetailsDTO;
import com.example.restaurant.security.services.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    public LoginOutputDTO execute(LoginInputDTO input){
        var usernamePassword = new UsernamePasswordAuthenticationToken(input.getEmail().trim(), input.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var userDetails = (UserDetailsDTO) auth.getPrincipal();

        var token = this.jwtTokenService.generateToken(userDetails.getUser());

        return new LoginOutputDTO(token);
    }
}