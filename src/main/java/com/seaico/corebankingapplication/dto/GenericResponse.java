package com.seaico.corebankingapplication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    @JsonProperty("Status")
    private int status;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("Data")
    private T data;

    public GenericResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
