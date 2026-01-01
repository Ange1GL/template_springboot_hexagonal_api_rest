package com.app.app.infraestructure.dto;


import lombok.Data;

@Data
public class CreateUserRequest
{
    private String name;
    private String email;
    private String password;
}
