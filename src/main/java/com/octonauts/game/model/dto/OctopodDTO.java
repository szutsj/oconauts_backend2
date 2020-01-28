package com.octonauts.game.model.dto;

public class OctopodDTO {
    private CrewDTO crew;
    private GupListDTO gups;
    private MedicineStockDTO medicineStock;
    private PatinentListDTO patinentList;
    private UserAndPoint userAndPoint;

    public OctopodDTO() {
    }

    public OctopodDTO(CrewDTO crew, GupListDTO gups, MedicineStockDTO medicineStock, PatinentListDTO patinentList, UserAndPoint userAndPoint) {
        this.crew = crew;
        this.gups = gups;
        this.medicineStock = medicineStock;
        this.patinentList = patinentList;
        this.userAndPoint = userAndPoint;
    }

    public CrewDTO getCrew() {
        return crew;
    }

    public void setCrew(CrewDTO crew) {
        this.crew = crew;
    }

    public GupListDTO getGups() {
        return gups;
    }

    public void setGups(GupListDTO gups) {
        this.gups = gups;
    }

    public MedicineStockDTO getMedicineStock() {
        return medicineStock;
    }

    public void setMedicineStock(MedicineStockDTO medicineStock) {
        this.medicineStock = medicineStock;
    }

    public PatinentListDTO getPatinentList() {
        return patinentList;
    }

    public void setPatinentList(PatinentListDTO patinentList) {
        this.patinentList = patinentList;
    }

    public UserAndPoint getUserAndPoint() {
        return userAndPoint;
    }

    public void setUserAndPoint(UserAndPoint userAndPoint) {
        this.userAndPoint = userAndPoint;
    }

}
