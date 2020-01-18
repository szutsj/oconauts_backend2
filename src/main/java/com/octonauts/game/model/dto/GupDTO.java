package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.GupType;

public class GupDTO {

    private GupType type;
    private boolean active;

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
}
