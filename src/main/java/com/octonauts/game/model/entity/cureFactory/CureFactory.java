package com.octonauts.game.model.entity.cureFactory;

import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.model.enums.SicknessType;

import java.util.ArrayList;
import java.util.List;

public abstract class CureFactory {

    public static List<Cure> getCureList(Sickness sickness, int level){
        if (sickness.getType().equals(SicknessType.FLU)){
            return cureForFlu(sickness, level);
        }
        if (sickness.getType().equals(SicknessType.INDIGESTION)){
            return cureForIndigestion(sickness, level);
        }
        if (sickness.getType().equals(SicknessType.INJURE)){
            return curesForInjure(sickness, level);
        }
        if (sickness.getType().equals(SicknessType.PAIN)){
            return cureForPain(sickness, level);
        }
        return cureForRash(sickness, level);
    }

    private static List<Cure> curesForInjure(Sickness sickness, int level){
        List<Cure> cures = new ArrayList<>();
        if (level == 1) {
            cures.add(new Cure(MedicineType.OINTMENT, sickness));
        }
        if (level <= 2 && level < 4) {
            cures.add(new Cure(MedicineType.BANDAGE, sickness));
        }
        if (level == 3) {
            cures.add(new Cure(MedicineType.RTG, sickness));
        }
        return cures;
    }

    private static List<Cure> cureForFlu(Sickness sickness, int level){
        List<Cure> cures = new ArrayList<>();
        if (level > 0 && level < 4){
            cures.add(new Cure(MedicineType.TEA, sickness));
        }
        if (level == 2){
            cures.add(new Cure(MedicineType.PILL, sickness));
        }
        if (level == 3){
            cures.add(new Cure(MedicineType.INJECTION, sickness));
        }
        return cures;
    }

    private static List<Cure> cureForIndigestion(Sickness sickness, int level){
        List<Cure> cures = new ArrayList<>();
        if (level > 0 && level < 4){
            cures.add(new Cure(MedicineType.TEA, sickness));
            cures.add(new Cure(MedicineType.DIET, sickness));
        }
        if (level == 2){
            cures.add(new Cure(MedicineType.PILL, sickness));
        }
        if (level == 3){
            cures.add(new Cure(MedicineType.INJECTION, sickness));
        }
        return cures;
    }

    private static List<Cure> cureForPain(Sickness sickness, int level){
        List<Cure> cures = new ArrayList<>();
        if (level > 0 && level < 4){
            cures.add(new Cure(MedicineType.PILL, sickness));
        }
        if (level == 2){
            cures.add(new Cure(MedicineType.INJECTION, sickness));
        }
        if (level == 3){
            cures.add(new Cure(MedicineType.RTG, sickness));
        }
        return cures;
    }

    private static List<Cure> cureForRash(Sickness sickness, int level) {
        List<Cure> cures = new ArrayList<>();
        if (level > 0 && level < 4) {
            cures.add(new Cure(MedicineType.OINTMENT, sickness));
        }
        if (level == 2) {
            cures.add(new Cure(MedicineType.BANDAGE, sickness));
        }
        if (level == 3) {
            cures.add(new Cure(MedicineType.PILL, sickness));
        }
        return cures;
    }

}
