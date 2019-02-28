package com.tylerbrady34gmail.familyclient.Models;

/**
 * Created by tyler on 3/20/2017.
 * Our settings for the family map
 */

public class Settings {
    /**If we should show life lines*/
    private boolean showLifeLines = true;
    /**The color of life lines variable*/
    private MyColor lifeColor = MyColor.GREEN;
    /**If we should show family tree lines*/
    private boolean showFamilyLines = true;
    /**The color of family lines*/
    private MyColor familyColor = MyColor.BLACK;
    /**If we should show spouse lines*/
    private boolean showSpouseLines = true;
    /**The color for spouse Lines*/
    private MyColor spouseColor = MyColor.MAGENTA;
    public Settings() {
    }

    public boolean isShowLifeLines() {
        return showLifeLines;
    }

    public void toggleShowLifeLines() {
        this.showLifeLines = !this.showLifeLines;
    }

    public boolean isShowFamilyLines() {
        return showFamilyLines;
    }

    public void toggleShowFamilyLines() {
        this.showFamilyLines = !this.showFamilyLines;
    }

    public boolean isShowSpouseLines() {
        return showSpouseLines;
    }

    public void toggleShowSpouseLines() {
        this.showSpouseLines = !this.showSpouseLines;
    }

    public MyColor getLifeColor() {
        return lifeColor;
    }

    public void setLifeColor(MyColor lifeColor) {
        this.lifeColor = lifeColor;
    }

    public MyColor getFamilyColor() {
        return familyColor;
    }

    public void setFamilyColor(MyColor familyColor) {
        this.familyColor = familyColor;
    }

    public MyColor getSpouseColor() {
        return spouseColor;
    }

    public void setSpouseColor(MyColor spouseColor) {
        this.spouseColor = spouseColor;
    }
}
