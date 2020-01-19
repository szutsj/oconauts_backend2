package com.octonauts.game.model.entity.sicknessFactory;

import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.enums.MedicineType;
import com.octonauts.game.model.enums.SicknessType;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name ="flus")
@Entity
public class Flu extends Sickness{

    public Flu() {
    }

    public Flu(int level){
        super(level);
        this.setType(SicknessType.FLU);
    }

}
