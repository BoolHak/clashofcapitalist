package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public abstract class ArmyFactory extends Building implements Serializable{

    public static int ARM_FACTORY_TYPE_TANKS = 0;
    //TODO the rest of army factory types

    private String armyFactoryId;
    private int armyFactoryCapacity;
    private int armFactoryType;

    public String getArmyFactoryId() {
        return armyFactoryId;
    }

    public void setArmyFactoryId(String armyFactoryId) {
        this.armyFactoryId = armyFactoryId;
    }

    public int getArmyFactoryCapacity() {
        return armyFactoryCapacity;
    }

    public void setArmyFactoryCapacity(int armyFactoryCapacity) {
        this.armyFactoryCapacity = armyFactoryCapacity;
    }

    public int getArmFactoryType() {
        return armFactoryType;
    }

    public void setArmFactoryType(int armFactoryType) {
        this.armFactoryType = armFactoryType;
    }
}
