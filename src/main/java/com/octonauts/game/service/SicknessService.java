package com.octonauts.game.service;

import com.octonauts.game.model.entity.cureFactory.Cure;
import com.octonauts.game.model.entity.cureFactory.CureFactory;
import com.octonauts.game.model.entity.sicknessFactory.Sickness;
import com.octonauts.game.model.entity.sicknessFactory.SicknessFactory;
import com.octonauts.game.model.enums.SicknessType;
import com.octonauts.game.repository.CureRepository;
import com.octonauts.game.repository.SicknessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SicknessService {

    private SicknessRepository sicknessRepository;
    private CureRepository cureRepository;

    @Autowired
    public SicknessService(SicknessRepository sicknessRepository, CureRepository cureRepository) {
        this.sicknessRepository = sicknessRepository;
        this.cureRepository = cureRepository;
    }

    public Sickness createNewSickness() {
        Sickness sickness = SicknessFactory.getSickness(randomSicknessTypeGenerator());
        save(sickness);
        sickness.setCureList(CureFactory.getCureList(sickness, sickness.getLevel()));
        return save(sickness);
    }

    public Sickness save(Sickness sickness){
        return sicknessRepository.save(sickness);
    }

    private SicknessType randomSicknessTypeGenerator(){
        int length = SicknessType.values().length;
        int randomNumber = (int)(Math.random() * length);
        int i = 0;
        for (SicknessType sicknessType : SicknessType.values()){
            if (i == randomNumber){
                return sicknessType;
            }
            i++;
        }
        return SicknessType.FLU;
    }

}
