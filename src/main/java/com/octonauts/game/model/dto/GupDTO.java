package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.GupType;

public class GupDTO {

    private GupType type;
    private boolean active;
    private int activationPoints;

    public GupDTO() {
    }

    public GupType getType() {
        return type;
    }

    public void setType(GupType type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getActivationPoints() {
        return activationPoints;
    }

    public void setActivationPoints(int activationPoints) {
        this.activationPoints = activationPoints;
    }
}
