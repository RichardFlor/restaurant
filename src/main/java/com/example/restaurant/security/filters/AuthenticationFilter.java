package com.example.restaurant.security.filters;

import com.example.restaurant.errors.ExceptionCode;
import com.example.restaurant.errors.exceptions.UnauthorizedException;
import com.example.restaurant.errors.i18n.MessageService;
import com.example.restaurant.repositories.UserJpaRepository;
import com.example.restaurant.security.dto.UserDetailsDTO;
import com.example.restaurant.security.services.JwtTokenService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserJpaRepository userJpaRepository;
    private final MessageService messageService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            final var token = this.extractToken(request);

            if (Objects.isNull(token)){
                filterChain.doFilter(request, response);
                return;
            }

            this.authenticate(this.jwtTokenService.getUserId(token), request);
            filterChain.doFilter(request, response);

        } catch (UnauthorizedException e) {
            response.sendError(
                    HttpStatus.UNAUTHORIZED.value(),
                    messageService.get(ExceptionCode.UNAUTHORIZED)
            );
        }

    }

    private String extractToken(HttpServletRequest request){
        final var token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer "))
            return null;

        return token.substring(7);
    }

    private void authenticate(UUID userId, HttpServletRequest request ){
        final var user = this.userJpaRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        final var userDetails = new UserDetailsDTO(user);

        final var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
