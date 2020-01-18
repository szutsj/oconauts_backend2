package com.octonauts.game.model.entity.sicknessFactory;

import com.octonauts.game.model.entity.Animal;
import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.enums.SicknessType;

import javax.persistence.*;
import java.util.List;

@Table(name ="sicknesses")
@Entity
public abstract class Sickness {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private SicknessType type;
    private int level;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sickness")
    private List<Cure> cureList;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animals_id")
    private Animal animal;

    public Sickness() {
    }

    public Sickness(int level){
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public SicknessType getType() {
        return type;
    }

    public void setType(SicknessType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Cure> getCureList() {
        return cureList;
    }

    public void setCureList(List<Cure> cureList) {
        this.cureList = cureList;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

}
