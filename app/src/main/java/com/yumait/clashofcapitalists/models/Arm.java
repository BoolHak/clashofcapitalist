package com.yumait.clashofcapitalists.models;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public abstract class Arm {
    public static int ARM_TYPE_AIR = 0;
    public static int ARM_TYPE_MARINE = 1;
    public static int ARM_TYPE_LAND = 2;
    public static int ARM_TYPE_GUN = 3;

    private String armId;
    private String armName;
    private int armAttackPower;
    private int armDefensePower;
    private int armType;

    public String getArmId() {
        return armId;
    }

    public void setArmId(String armId) {
        this.armId = armId;
    }

    public String getArmName() {
        return armName;
    }

    public void setArmName(String armName) {
        this.armName = armName;
    }

    public int getArmAttackPower() {
        return armAttackPower;
    }

    public void setArmAttackPower(int armAttackPower) {
        this.armAttackPower = armAttackPower;
    }

    public int getArmDefensePower() {
        return armDefensePower;
    }

    public void setArmDefensePower(int armDefensePower) {
        this.armDefensePower = armDefensePower;
    }

    public int getArmType() {
        return armType;
    }

    public void setArmType(int armType) {
        this.armType = armType;
    }

    public boolean isArmTypeAir() {
        return armType == ARM_TYPE_AIR;
    }

    public boolean isArmTypeMarine() {
        return armType == ARM_TYPE_MARINE;
    }

    public boolean isArmTypeLand() {
        return armType == ARM_TYPE_LAND;
    }

    public boolean isArmTypeGun() {
        return armType == ARM_TYPE_GUN;
    }

}
