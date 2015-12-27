package com.yumait.clashofcapitalists.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Factory implements Serializable {

    public static  final int FACTORY_FOOD = 0;
    public static  final int FACTORY_COGS = 1;
    public static  final int FACTORY_TECH = 2;


    private String factoryId;
    private int factoryType;
    private double factoryCapacity;
    private List<Machines> factoryMachines;

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public int getFactoryType() {
        return factoryType;
    }

    public void setFactoryType(int factoryType) {
        this.factoryType = factoryType;
    }

    public double getFactoryCapacity() {
        return factoryCapacity;
    }

    public void setFactoryCapacity(double factoryCapacity) {
        this.factoryCapacity = factoryCapacity;
    }

    public boolean isFactoryFood() {
        return factoryType == FACTORY_FOOD;
    }

    public boolean isFactoryCogs() {
        return factoryType == FACTORY_COGS;
    }

    public boolean isFactoryTech() {
        return factoryType == FACTORY_TECH;
    }

    public List<Machines> getFactoryMachines() {
        return factoryMachines;
    }

    public void setFactoryMachines(List<Machines> factoryMachines) {
        this.factoryMachines = factoryMachines;
    }
}
