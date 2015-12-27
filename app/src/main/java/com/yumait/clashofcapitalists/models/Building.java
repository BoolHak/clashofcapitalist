package com.yumait.clashofcapitalists.models;

import java.io.Serializable;

/**
 * Created by JC SAVIGNY on 26/12/2015.
 */
public abstract class Building  implements Serializable{

    private int buildingSurface;

    public int getBuildingSurface() {
        return buildingSurface;
    }

    public void setBuildingSurface(int buildingSurface) {
        this.buildingSurface = buildingSurface;
    }
}
