package com.example.restaurant.dtos.user.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserInputDTO {
    @NotBlank
    @Schema(example = "Richard", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Email
    @NotNull
    @Schema(example = "richard@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

}
