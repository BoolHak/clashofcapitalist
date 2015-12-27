package com.yumait.clashofcapitalists.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public class Colony implements Serializable {

    private String colonyId;
    private String colonyName;
    private String colonyOwnerId;
    private List<Building> colonyDefense;
    private int colonyGoldValue;

    public String getColonyId() {
        return colonyId;
    }

    public void setColonyId(String colonyId) {
        this.colonyId = colonyId;
    }

    public String getColonyName() {
        return colonyName;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }

    public String getColonyOwnerId() {
        return colonyOwnerId;
    }

    public void setColonyOwnerId(String colonyOwnerId) {
        this.colonyOwnerId = colonyOwnerId;
    }

    public List<Building> getColonyDefense() {
        return colonyDefense;
    }

    public void setColonyDefense(List<Building> colonyDefense) {
        this.colonyDefense = colonyDefense;
    }

    public int getColonyGoldValue() {
        return colonyGoldValue;
    }

    public void setColonyGoldValue(int colonyGoldValue) {
        this.colonyGoldValue = colonyGoldValue;
    }
}
