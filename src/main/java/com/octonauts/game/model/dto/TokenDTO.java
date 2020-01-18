package com.octonauts.game.model.dto;

import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition
public class TokenDTO {

    private String token;

    public TokenDTO() {
    }

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}