package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.MedicineType;

public class MedicineDTO {
    private MedicineType type;

    public MedicineDTO() {
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }
}
