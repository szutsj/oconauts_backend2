package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.AnimalType;

import java.sql.Timestamp;

public class PatientDTO {
    private long id;
    private AnimalType type;
    private SicknessDTO sicknessDTO;
    private Timestamp treatmentStartedAt;
    private Timestamp treatmentFinishedAt;
    private int pointsGivenForCure;

    public PatientDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public SicknessDTO getSicknessDTO() {
        return sicknessDTO;
    }

    public void setSicknessDTO(SicknessDTO sicknessDTO) {
        this.sicknessDTO = sicknessDTO;
    }

    public void setTreatmentStartedAt(Timestamp treatmentStartedAt) {
        this.treatmentStartedAt = treatmentStartedAt;
    }

    public Timestamp getTreatmentFinishedAt() {
        return treatmentFinishedAt;
    }

    public void setTreatmentFinishedAt(Timestamp treatmentFinishedAt) {
        this.treatmentFinishedAt = treatmentFinishedAt;
    }

    public Timestamp getTreatmentStartedAt() {
        return treatmentStartedAt;
    }

    public int getPointsGivenForCure() {
        return pointsGivenForCure;
    }

    public void setPointsGivenForCure(int pointsGivenForCure) {
        this.pointsGivenForCure = pointsGivenForCure;
    }

}
