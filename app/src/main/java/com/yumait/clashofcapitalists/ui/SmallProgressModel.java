package com.yumait.clashofcapitalists.ui;

/**
 * Created by Bilel Methnani on 12/27/2015.
 */
public class SmallProgressModel {
    private int image;
    private int value;
    private int max;

    public SmallProgressModel(int image, int value, int max) {
        this.image = image;
        this.value = value;
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
