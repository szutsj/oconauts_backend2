package com.octonauts.game.model.entity;

import com.octonauts.game.contsants.ActivationPoints;
import com.octonauts.game.model.enums.GupType;

import javax.persistence.*;

@Table(name = "gups")
@Entity
public class Gup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private GupType type;
    private boolean active;
    private int pointsForActivate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "octopods_id")
    private Octopod octopod;

    public Gup() {
    }

    public Gup(GupType type, boolean active, Octopod octopod) {
        this.type = type;
        this.active = active;
        this.octopod = octopod;
        this.pointsForActivate = setPointsForActivateByType(type);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    private int setPointsForActivateByType(GupType type) {
        if (type.equals(GupType.GUPA)){
            return ActivationPoints.ACTIVATE_POINT_GUPA;
        }
        if (type.equals(GupType.GUPB)){
            return ActivationPoints.ACTIVATE_POINT_GUPB;
        }
        if (type.equals(GupType.GUPC)){
            return ActivationPoints.ACTIVATE_POINT_GUPC;
        }
        if (type.equals(GupType.GUPD)){
            return ActivationPoints.ACTIVATE_POINT_GUPD;
        }
        return 0;
    }

}
