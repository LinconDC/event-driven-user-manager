package br.com.usermanager.model.response;

public record UserResponse(
        String id,
        String name,
        String email
) {}