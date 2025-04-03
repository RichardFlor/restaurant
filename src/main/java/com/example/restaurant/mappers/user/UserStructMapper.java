package com.example.restaurant.mappers.user;


import com.example.restaurant.dtos.user.input.CreateUserInputDTO;
import com.example.restaurant.dtos.user.output.UserDetailedOutputDTO;
import com.example.restaurant.entities.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStructMapper {
    UserDetailedOutputDTO toUserDetailedOutputDTO(User entity);
    User toEntity(CreateUserInputDTO dto);
}
