package com.octonauts.game.model.dto;

import java.util.List;

public class GupListDTO {

    List<GupDTO> gups;

    public GupListDTO(List<GupDTO> gups) {
        this.gups = gups;
    }

    public GupListDTO() {
    }

    public List<GupDTO> getGups() {
        return gups;
    }

    public void setGups(List<GupDTO> gups) {
        this.gups = gups;
    }
}
