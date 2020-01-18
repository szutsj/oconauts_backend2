package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.model.enums.SicknessType;

import java.util.List;

public class SicknessDTO {
    private SicknessType type;
    private int level;
    private List<MedicineType> medicinesNeeded;

    public SicknessDTO() {
    }

    public SicknessType getType() {
        return type;
    }

    public void setType(SicknessType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<MedicineType> getMedicinesNeeded() {
        return medicinesNeeded;
    }

    public void setMedicinesNeeded(List<MedicineType> medicinesNeeded) {
        this.medicinesNeeded = medicinesNeeded;
    }

}
