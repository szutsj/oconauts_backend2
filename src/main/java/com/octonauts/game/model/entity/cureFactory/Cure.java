package com.octonauts.game.model.entity.cureFactory;

import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import com.octonauts.game.model.enums.MedicineType;

import javax.persistence.*;

@Table(name = "Cures")
@Entity
public class Cure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private MedicineType type;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sicknesses_id")
    private Sickness sickness;


    public Cure(MedicineType type, Sickness sickness) {
        this.type = type;
        this.sickness = sickness;
    }

    public Cure() {
    }

    public Cure(MedicineType type, Octopod octopod) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MedicineType getType() {
        return type;
    }

    public void setType(MedicineType type) {
        this.type = type;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }
}
