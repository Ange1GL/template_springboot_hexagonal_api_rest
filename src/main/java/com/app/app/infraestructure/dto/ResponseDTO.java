package com.app.app.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ResponseDTO<T> {

    private String responseMessage;
    private T data;
    private List<ErrorDetailDTO> errorList;

}
