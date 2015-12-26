package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Factory extends Building implements Serializable {

    public static  final int FACTORY_FOOD = 0;
    public static  final int FACTORY_COGS = 1;
    public static  final int FACTORY_TECH = 2;


    private String factoryId;
    private int factoryType;
    private double factoryCapacity;


    public boolean isFactoryFood() {
        return factoryType == FACTORY_FOOD;
    }

    public boolean isFactoryCogs() {
        return factoryType == FACTORY_COGS;
    }

    public boolean isFactoryTech() {
        return factoryType == FACTORY_TECH;
    }

}
