package com.octonauts.game.model.dto;

import com.octonauts.game.model.enums.CrewMembers;

public class CrewMemberDTO {

    private CrewMembers name;
    private boolean active;

    public CrewMemberDTO() {
    }

    public CrewMembers getName() {
        return name;
    }

    public void setName(CrewMembers name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
