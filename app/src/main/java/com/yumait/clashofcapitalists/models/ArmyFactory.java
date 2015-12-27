package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public abstract class ArmyFactory extends Building implements Serializable{

    public static int ARM_FACTORY_TYPE_TANKS = 0;
    public static int ARM_FACTORY_TYPE_PLANES = 1;
    //TODO the rest of army factory types

    private int armyFactoryType;
    private int armyFactoryLevel;

    public int getArmyFactoryType() {
        return armyFactoryType;
    }

    public void setArmyFactoryType(int armyFactoryType) {
        this.armyFactoryType = armyFactoryType;
    }

    public int getArmyFactoryLevel() {
        return armyFactoryLevel;
    }

    public void setArmyFactoryLevel(int armyFactoryLevel) {
        this.armyFactoryLevel = armyFactoryLevel;
    }
}
