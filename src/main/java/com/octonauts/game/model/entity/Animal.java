package com.octonauts.game.model.entity;

import com.octonauts.game.model.enums.AnimalType;
import com.octonauts.game.model.entity.sicknessFactory.Sickness;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "animals")
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private AnimalType type;
    @OneToOne(mappedBy = "animal", cascade = CascadeType.ALL)
    private Sickness sickness;
    private LocalDateTime treatmentStartedAt;
    private LocalDateTime treatmentFinishedAt;
    private int pointsGivenForCure;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private User user;

    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public Sickness getSickness() {
        return sickness;
    }

    public void setSickness(Sickness sickness) {
        this.sickness = sickness;
    }

    public LocalDateTime getTreatmentStartedAt() {
        return treatmentStartedAt;
    }

    public void setTreatmentStartedAt(LocalDateTime treatmentStartedAt) {
        this.treatmentStartedAt = treatmentStartedAt;
    }

    public LocalDateTime getTreatmentFinishedAt() {
        return treatmentFinishedAt;
    }

    public void setTreatmentFinishedAt(LocalDateTime treatmentFinishedAt) {
        this.treatmentFinishedAt = treatmentFinishedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPointsGivenForCure() {
        return pointsGivenForCure;
    }

    public void setPointsGivenForCure(int pointsGivenForCure) {
        this.pointsGivenForCure = pointsGivenForCure;
    }
}
