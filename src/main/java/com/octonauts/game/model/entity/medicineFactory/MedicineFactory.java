package com.octonauts.game.model.entity.medicineFactory;

import com.octonauts.game.model.entity.Octopod;
import com.octonauts.game.model.enums.MedicineType;

public abstract class MedicineFactory {

    public static Medicine getMedicine(MedicineType medicineType, Octopod octopod){
        if (MedicineType.TEA.equals(medicineType)){
            return (new Medicine(MedicineType.TEA, octopod));
        }
        if (MedicineType.BANDAGE.equals(medicineType)){
            return (new Medicine(MedicineType.BANDAGE, octopod));
        }
        if (MedicineType.DIET.equals(medicineType)){
            return (new Medicine(MedicineType.DIET, octopod));
        }
        if (MedicineType.INJECTION.equals(medicineType)){
            return (new Medicine(MedicineType.INJECTION, octopod));
        }
        if (MedicineType.OINTMENT.equals(medicineType)){
            return (new Medicine(MedicineType.OINTMENT, octopod));
        }
        return (new Medicine(MedicineType.PILL, octopod));
    }

    public static MedicineType getMedicineType(String type) {
        if (MedicineType.PILL.name().equalsIgnoreCase(type)){
            return MedicineType.PILL;
        }
        if (MedicineType.OINTMENT.name().equalsIgnoreCase(type)){
            return MedicineType.OINTMENT;
        }
        if (MedicineType.INJECTION.name().equalsIgnoreCase(type)){
            return MedicineType.INJECTION;
        }
        if (MedicineType.DIET.name().equalsIgnoreCase(type)){
            return MedicineType.DIET;
        }
        if (MedicineType.BANDAGE.name().equalsIgnoreCase(type)){
            return MedicineType.BANDAGE;
        }
        if (MedicineType.TEA.name().equalsIgnoreCase(type)){
            return MedicineType.TEA;
        }
        return MedicineType.RTG;
    }

}
