package com.octonauts.game.model.dto;

import java.util.List;

public class MedicineStockDTO {

    List<MedicineDTO> medicines;

    public MedicineStockDTO(List<MedicineDTO> medicines) {
        this.medicines = medicines;
    }

    public MedicineStockDTO() {
    }

    public List<MedicineDTO> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<MedicineDTO> medicines) {
        this.medicines = medicines;
    }
}
