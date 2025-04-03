package com.example.restaurant.rest.specs;


import com.example.restaurant.dtos.user.input.ChangePasswordInputDTO;
import com.example.restaurant.dtos.user.input.CreateUserInputDTO;
import com.example.restaurant.dtos.user.output.UserDetailedOutputDTO;
import com.example.restaurant.errors.ErrorResponse;
import com.example.restaurant.rest.specs.commons.response.error.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@ApiResponseInternalServerError
@Tag(name = "2. User", description = "User operations")
public interface UserControllerSpecs {

    @Operation(summary = "Create user")
    @ApiResponseBadRequest
    @ApiResponseDuplicatedResource
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "500", description = "Email Not Sent", content = {
                    @Content(schema = @Schema(implementation = ErrorResponse.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)
            })
    })
    @ResponseStatus(HttpStatus.CREATED)
    void create(
            HttpServletRequest httpRequest,
            @RequestPart(required = false, name = "image") MultipartFile image,
            @Parameter(required = true,
                    schema = @Schema(implementation = CreateUserInputDTO.class)
            ) @RequestPart(name = "body") String body
    );

    @Operation(summary = "Change password")
    @ApiResponseNotFound
    @ApiResponseBadRequest
    @ApiResponseBusinessRuleException
    @ApiResponse(responseCode = "204", description = "No Content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void passwordRecovery(@RequestBody ChangePasswordInputDTO request);

    @Operation(summary = "Find user by id")
    @ApiResponseNotFound
    @ApiResponseForbidden
    @ApiResponseBadRequest
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(schema = @Schema(implementation = UserDetailedOutputDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)
    })
    @SecurityRequirement(name = "jwt")
    @ResponseStatus(HttpStatus.OK)
    UserDetailedOutputDTO findById(@PathVariable UUID id);

    @Operation(summary = "Delete current logged in user")
    @ApiResponseForbidden
    @ApiResponse(responseCode = "204", description = "No Content")
    @SecurityRequirement(name = "jwt")
    void delete();
}
