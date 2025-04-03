package com.example.restaurant.usecases.user;


import com.example.restaurant.dtos.user.output.UserDetailedOutputDTO;
import com.example.restaurant.entities.user.User;
import com.example.restaurant.errors.exceptions.EntityNotFoundException;
import com.example.restaurant.mappers.user.UserStructMapper;
import com.example.restaurant.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindUserByIdUseCase {
    private final UserJpaRepository userJpaRepository;
    private final UserStructMapper userStructMapper;

    public UserDetailedOutputDTO execute(UUID id) {
        var user = this.userJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(User.class));

        return this.userStructMapper.toUserDetailedOutputDTO(user);
    }
}
