package com.octonauts.game.model.dto;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition
public class RegisterResponseDTO {

    @ApiModelProperty(position = 1)
    private Long userId;
    @ApiModelProperty(position = 2)
    private String username;
    @ApiModelProperty(position = 3)
    private Long octopodId;


    public RegisterResponseDTO() {
    }

    public RegisterResponseDTO(Long userId, String username, Long octopodId) {
        this.userId = userId;
        this.username = username;
        this.octopodId = octopodId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getOctopodId() {
        return octopodId;
    }

    public void setOctopodId(Long octopodId) {
        this.octopodId = octopodId;
    }
}