package com.app.app.infraestructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetailDTO {

    private Integer errorCode;
    private String errorMessage;
    public String errorSource;
}
