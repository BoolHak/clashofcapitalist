package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public abstract class Building  implements Serializable{

    private String buildingId;
    /**
     * BuildingSurface is always mesured by squares (min 2x2 squares)
     */
    private int buildingSurface;
    private int buildingAttackPower;
    private int buildingResistPower;
    private int buildingCapacity;
    private int buildingLevel;

    public int getBuildingSurface() {
        return buildingSurface;
    }

    public void setBuildingSurface(int buildingSurface) {
        this.buildingSurface = buildingSurface;
    }

    public int getBuildingResistPower() {
        return buildingResistPower;
    }

    public void setBuildingResistPower(int buildingResistPower) {
        this.buildingResistPower = buildingResistPower;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public int getBuildingAttackPower() {
        return buildingAttackPower;
    }

    public void setBuildingAttackPower(int buildingAttackPower) {
        this.buildingAttackPower = buildingAttackPower;
    }

    public int getBuildingCapacity() {
        return buildingCapacity;
    }

    public void setBuildingCapacity(int buildingCapacity) {
        this.buildingCapacity = buildingCapacity;
    }

    public int getBuildingLevel() {
        return buildingLevel;
    }

    public void setBuildingLevel(int buildingLevel) {
        this.buildingLevel = buildingLevel;
    }
}
