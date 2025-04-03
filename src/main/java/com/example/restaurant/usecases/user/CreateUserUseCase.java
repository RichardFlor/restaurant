package com.example.restaurant.usecases.user;

import com.example.restaurant.dtos.user.input.CreateUserInputDTO;
import com.example.restaurant.entities.user.User;
import com.example.restaurant.enums.UserRole;
import com.example.restaurant.errors.exceptions.DuplicatedResourceException;
import com.example.restaurant.mappers.user.UserStructMapper;
import com.example.restaurant.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.text.MessageFormat;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserJpaRepository userJpaRepository;
    private final UserStructMapper userStructMapper;

    @Transactional
    public void execute(CreateUserInputDTO input) {
        if (this.userJpaRepository.findByEmail(input.getEmail()).isPresent())
            throw new DuplicatedResourceException(User.class, Map.of("email", input.getEmail()));

        var user = userStructMapper.toEntity(input).withRole(UserRole.WAITER);
        user.setPassword(new BCryptPasswordEncoder().encode(input.getPassword()));

        log.warn("Creating user with email: {} ", input.getEmail());
        var createdUser = userJpaRepository.save(user);

    }


    private String generateUrlToValidateEmail(UUID userId) {
        var baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/v1";
        return MessageFormat.format(
                "{0}/user/{1}/validate-email",
                baseUrl,
                userId
        );
    }
}
