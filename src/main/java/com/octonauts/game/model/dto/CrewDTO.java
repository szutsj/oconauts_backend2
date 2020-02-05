package com.octonauts.game.model.dto;

import java.util.List;

public class CrewDTO {
    List<CrewMemberDTO> crewMembers;


    public CrewDTO() {
    }

    public CrewDTO(List<CrewMemberDTO> crewMembers) {
        this.crewMembers = crewMembers;
    }

    public List<CrewMemberDTO> getCrewMembers() {
        return crewMembers;
    }

    public void setCrewMembers(List<CrewMemberDTO> crewMembers) {
        this.crewMembers = crewMembers;
    }
}
