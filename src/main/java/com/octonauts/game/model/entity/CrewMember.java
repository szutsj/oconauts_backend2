package com.octonauts.game.model.entity;

import com.octonauts.game.contsants.ActivationPoints;
import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.enums.CrewMembers;
import com.octonauts.game.model.enums.MedicineType;

import javax.persistence.*;

@Table(name = "crewMembers")
@Entity
public class CrewMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private CrewMembers name;
    private boolean active;
    private int pointsForActivate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "octopods_id")
    private Octopod octopod;

    public CrewMember() {
    }

    public CrewMember(CrewMembers name, boolean active, Octopod octopod) {
        this.name = name;
        this.active = active;
        this.octopod = octopod;
        this.pointsForActivate = setPointsForActivateByType(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getPointsForActivate() {
        return pointsForActivate;
    }

    public void setPointsForActivate(int pointsForActivate) {
        this.pointsForActivate = pointsForActivate;
    }

    public Octopod getOctopod() {
        return octopod;
    }

    public void setOctopod(Octopod octopod) {
        this.octopod = octopod;
    }

    public int setPointsForActivateByType(CrewMembers name){
        if (name.equals(CrewMembers.PESO)){
            return ActivationPoints.ACTIVATE_POINT_PESO;
        }
        if (name.equals(CrewMembers.CAPTAIN)){
            return ActivationPoints.ACTIVATE_POINT_CAPTAIN;
        }
        if (name.equals(CrewMembers.KWAZII)){
            return ActivationPoints.ACTIVATE_POINT_KWAZII;
        }
        if (name.equals(CrewMembers.DASHI)){
            return ActivationPoints.ACTIVATE_POINT_DASHY;
        }
        if (name.equals(CrewMembers.SHELLINGTON)){
            return ActivationPoints.ACTIVATE_POINT_SHELLINGTON;
        }
        if (name.equals(CrewMembers.TWEAK)){
            return ActivationPoints.ACTIVATE_POINT_TWEAK;
        }
        if (name.equals(CrewMembers.PROFESSOR)){
            return ActivationPoints.ACTIVATE_POINT_PROFESSOR;
        }
        if (name.equals(CrewMembers.TUNIP)){
            return ActivationPoints.ACTIVATE_POINT_TUNIP;
        }
        return 0;
    }

}
