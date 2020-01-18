package com.octonauts.game.model.entity;

import com.octonauts.game.contsants.MedicinePrices;
import com.octonauts.game.model.enums.MedicineType;

import javax.persistence.*;

@Table(name = "Medicines")
@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private MedicineType type;
    private int price;
    private boolean used;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "octopods_id")
    private Octopod octopod;

    public Medicine(MedicineType type) {
        this.type = type;
    }

    public Medicine() {
    }

    public Medicine(MedicineType type, Octopod octopod) {
        this.price = setPriceByType(type);
        this.type = type;
        this.octopod = octopod;
        this.used = false;
    }

    private int setPriceByType(MedicineType type) {
        if (type.equals(MedicineType.BANDAGE)){
            return MedicinePrices.BANDAGE_PRICE;
        }
        if (type.equals(MedicineType.DIET)){
            return MedicinePrices.DIET_PRICE;
        }
        if (type.equals(MedicineType.TEA)){
            return MedicinePrices.TEA_PRICE;
        }
        if (type.equals(MedicineType.INJECTION)){
            return MedicinePrices.INJECTION_PRICE;
        }
        if (type.equals(MedicineType.OINTMENT)){
            return MedicinePrices.OINTMENT_PRICE;
        }
        if (type.equals(MedicineType.PILL)){
            return MedicinePrices.PILL_PRICE;
        }
        if (type.equals(MedicineType.RTG)){
            return MedicinePrices.RTG_PRICE;
        }
        return 1;
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

    public int getPrice() {
        return price;
    }

    public Octopod getOctopod() {
        return octopod;
    }

    public void setOctopod(Octopod octopod) {
        this.octopod = octopod;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
