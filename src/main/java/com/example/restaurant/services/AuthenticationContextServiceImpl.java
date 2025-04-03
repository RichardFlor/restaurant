package com.example.restaurant.services;


import com.example.restaurant.entities.user.User;
import com.example.restaurant.errors.exceptions.EntityNotFoundException;
import com.example.restaurant.errors.exceptions.ForbiddenException;
import com.example.restaurant.security.dto.UserDetailsDTO;
import com.example.restaurant.usecases.user.FindUserByIdUseCase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationContextServiceImpl {
    private final FindUserByIdUseCase findUserByIdUseCase;

    public UserDetailsDTO getData() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof Optional)
            return this.handleOptionalData((Optional<UserDetailsDTO>) principal);

        if (principal instanceof UserDetailsDTO)
            return (UserDetailsDTO) principal;

        throw new ForbiddenException();
    }

    private UserDetailsDTO handleOptionalData(Optional<UserDetailsDTO> principal) {
        principal.orElseThrow(() -> new EntityNotFoundException(UserDetailsDTO.class));
        return principal.get();
    }

    public User getLoggedUser() {
        return this.getData().getUser();
    }
}
