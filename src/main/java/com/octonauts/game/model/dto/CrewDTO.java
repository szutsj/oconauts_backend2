package com.octonauts.game.model.dto;

import java.util.List;

public class CrewDTO {

    List<CrewMemberDTO> crew;

    public CrewDTO(List<CrewMemberDTO> crew) {
        this.crew = crew;
    }

    public CrewDTO() {
    }

    public List<CrewMemberDTO> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewMemberDTO> crew) {
        this.crew = crew;
    }
}
