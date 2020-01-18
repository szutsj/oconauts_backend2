package com.octonauts.game.model.dto;

import java.util.List;

public class PatinentListDTO {

    private List<PatientDTO> patients;

    public PatinentListDTO() {
    }

    public PatinentListDTO(List<PatientDTO> patients) {
        this.patients = patients;
    }

    public List<PatientDTO> getPatients() {
        return patients;
    }

    public void setPatients(List<PatientDTO> patients) {
        this.patients = patients;
    }
}
