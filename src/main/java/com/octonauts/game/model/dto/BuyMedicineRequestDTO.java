package com.octonauts.game.model.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition
public class BuyMedicineRequestDTO {

    @ApiModelProperty(position = 1)
    private String type;

    public BuyMedicineRequestDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}