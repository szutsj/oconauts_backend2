package com.octonauts.game.model.entity;

import javax.persistence.*;
import java.util.List;

@Table(name = "octopods")
@Entity
public class Octopod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "octopod")
    private List<CrewMember> crewMemberList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "octopod")
    private List<Gup> gupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "octopod")
    private List<Medicine> medicineList;

    public Octopod() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CrewMember> getCrewMemberList() {
        return crewMemberList;
    }

    public void setCrewMemberList(List<CrewMember> crewMemberList) {
        this.crewMemberList = crewMemberList;
    }

    public List<Gup> getGupList() {
        return gupList;
    }

    public void setGupList(List<Gup> gupList) {
        this.gupList = gupList;
    }

    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

}
