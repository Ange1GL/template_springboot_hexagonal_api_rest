package com.app.app.infraestructure.dto;

public record LoginRequest (
    String email,
    String password
) {}


