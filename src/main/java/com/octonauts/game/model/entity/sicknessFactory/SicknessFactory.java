package com.octonauts.game.model.entity.sicknessFactory;

import com.octonauts.game.model.enums.SicknessType;


public abstract class SicknessFactory {

    public static Sickness getSickness(SicknessType type){
        if (type.equals(SicknessType.FLU)){
            return new Flu(randomLevel());
        }
        if (type.equals(SicknessType.INDIGESTION)){
            return new Indigestion(randomLevel());
        }
        if (type.equals(SicknessType.INJURE)){
            return new Injure(randomLevel());
        }
        if (type.equals(SicknessType.PAIN)){
            return new Pain(randomLevel());
        }
        if (type.equals(SicknessType.RASH)){
            return new Rash(randomLevel());
        }
        return null;
    }

    private static int  randomLevel(){
        return (int)(Math.random() * 3) + 1;
    }

}
