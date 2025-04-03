package com.example.restaurant.rest.specs;

import com.example.restaurant.dtos.auth.input.LoginInputDTO;
import com.example.restaurant.dtos.auth.output.LoginOutputDTO;
import com.example.restaurant.rest.specs.commons.response.error.ApiResponseBadRequest;
import com.example.restaurant.rest.specs.commons.response.error.ApiResponseForbidden;
import com.example.restaurant.rest.specs.commons.response.error.ApiResponseInternalServerError;
import com.example.restaurant.rest.specs.commons.response.error.ApiResponseUnauthorized;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;


@ApiResponseBadRequest
@ApiResponseInternalServerError
@Tag(name = "1. Auth", description = "Auth operations")
public interface AuthControllerSpecs {

    @Operation(summary = "Login")
    @ApiResponseForbidden
    @ApiResponseUnauthorized
    @ApiResponse(responseCode = "200", description = "Ok", content = {
            @Content(schema = @Schema(implementation = LoginOutputDTO.class),
                    mediaType = MediaType.APPLICATION_JSON_VALUE)
    })
    LoginOutputDTO login(@RequestBody LoginInputDTO request);

}