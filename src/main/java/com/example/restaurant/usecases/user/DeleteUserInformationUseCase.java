package com.example.restaurant.usecases.user;


import com.example.restaurant.repositories.UserJpaRepository;
import com.example.restaurant.services.AuthenticationContextServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserInformationUseCase {
    private final UserJpaRepository repository;
    private final AuthenticationContextServiceImpl authService;

    public void execute() {
        var user = authService.getLoggedUser();
        repository.deleteById(user.getId());
    }
}
