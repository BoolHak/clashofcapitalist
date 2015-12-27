package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 27/12/2015.
 */
public class Soldier implements Serializable {

    public int SOLDIER_TYPE_NORMAL = 0;
    public int SOLDIER_TYPE_MARINES = 1;
    public int SOLDIER_TYPE_SPECIAL_FORCES = 3;
    public int SOLDIER_TYPE_SPY = 4;

    private int soldierId;
    private int soldierLevel;
    private int soldierType;
    private int soldierArmId;

    public int getSoldierId() {
        return soldierId;
    }

    public void setSoldierId(int soldierId) {
        this.soldierId = soldierId;
    }

    public int getSoldierLevel() {
        return soldierLevel;
    }

    public void setSoldierLevel(int soldierLevel) {
        this.soldierLevel = soldierLevel;
    }

    public int getSoldierArmId() {
        return soldierArmId;
    }

    public void setSoldierArmId(int soldierArmId) {
        this.soldierArmId = soldierArmId;
    }

    public int getSoldierType() {
        return soldierType;
    }

    public void setSoldierType(int soldierType) {
        this.soldierType = soldierType;
    }

    public boolean isSoldierTypeNormal() {
        return soldierType == SOLDIER_TYPE_NORMAL;
    }

    public boolean isSoldierTypeMarines() {
        return soldierType == SOLDIER_TYPE_MARINES;
    }

    public boolean isSoldierTypeSpecialForces() {
        return soldierType == SOLDIER_TYPE_SPECIAL_FORCES;
    }

    public boolean isSoldierTypeSpy() {
        return soldierType == SOLDIER_TYPE_SPY;
    }
}
