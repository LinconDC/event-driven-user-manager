package br.com.usermanager.model.request;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String userName,
        @NotBlank String userEmail,
        @NotBlank String userPassword
) {}