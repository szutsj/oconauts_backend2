package com.octonauts.game.model.dto;

public class OctopodDTO {
    private CrewDTO crewStatus;
    private GupListDTO gupStatus;
    private MedicineStockDTO medicineStock;
    private PatinentListDTO patinentList;
    private UserAndPoint userAndPoint;

    public OctopodDTO() {
    }

    public OctopodDTO(CrewDTO crewStatus, GupListDTO gupStatus, MedicineStockDTO medicineStock, PatinentListDTO patinentList, UserAndPoint userAndPoint) {
        this.crewStatus = crewStatus;
        this.gupStatus = gupStatus;
        this.medicineStock = medicineStock;
        this.patinentList = patinentList;
        this.userAndPoint = userAndPoint;
    }

    public CrewDTO getCrewStatus() {
        return crewStatus;
    }

    public void setCrewStatus(CrewDTO crewStatus) {
        this.crewStatus = crewStatus;
    }

    public GupListDTO getGupStatus() {
        return gupStatus;
    }

    public void setGupStatus(GupListDTO gupStatus) {
        this.gupStatus = gupStatus;
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
