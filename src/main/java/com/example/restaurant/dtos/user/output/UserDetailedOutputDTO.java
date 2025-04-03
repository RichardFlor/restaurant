package com.example.restaurant.dtos.user.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailedOutputDTO {
    private Long id;

    @Schema(example = "Jorge")
    private String name;

    @Schema(example = "jorge@gmail")
    private String email;

}
