package com.example.restaurant.rest.controllers;



import com.example.restaurant.dtos.user.input.ChangePasswordInputDTO;
import com.example.restaurant.dtos.user.input.CreateUserInputDTO;
import com.example.restaurant.dtos.user.output.UserDetailedOutputDTO;
import com.example.restaurant.rest.specs.UserControllerSpecs;
import com.example.restaurant.usecases.user.ChangePasswordUseCase;
import com.example.restaurant.usecases.user.CreateUserUseCase;
import com.example.restaurant.usecases.user.DeleteUserInformationUseCase;
import com.example.restaurant.usecases.user.FindUserByIdUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerSpecs {
    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;
    private final DeleteUserInformationUseCase deleteUserInformationUseCase;



    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void create(
            HttpServletRequest httpRequest,
            @RequestPart(required = false, name = "image") MultipartFile image,
            @RequestPart(name = "body") String body
    ) {
        var request = (CreateUserInputDTO) httpRequest.getAttribute("request");

        Optional.ofNullable(httpRequest.getAttribute("imageURI"))
                .map(Object::toString);

        this.createUserUseCase.execute(request);
    }

    @PatchMapping("/change-password")
    public void passwordRecovery(@RequestBody @Valid ChangePasswordInputDTO request) {
        this.changePasswordUseCase.execute(request);
    }

    @GetMapping("/{id}")
    public UserDetailedOutputDTO findById(@PathVariable UUID id) {
        return this.findUserByIdUseCase.execute(id);
    }


    @DeleteMapping
    public void delete() {
        this.deleteUserInformationUseCase.execute();
    }
}
