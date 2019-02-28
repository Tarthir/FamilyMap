package com.tylerbrady34gmail.familyclient.Models;

import android.graphics.Color;

/**
 * Created by tyler on 3/20/2017.
 * Our color values
 */

public enum MyColor {
    MAGENTA(Color.MAGENTA),
    RED(Color.RED),
    BLUE(Color.BLUE),
    CYAN(Color.CYAN),
    GREEN(Color.GREEN),
    GRAY(Color.GRAY),
    YELLOW(Color.YELLOW),
    DARKGREY(Color.DKGRAY),
    LIGHTGRAY(Color.LTGRAY),
    BLACK(Color.BLACK);

    private int color;

    MyColor(int c) {
        color = c;
    }


    public int getColor() {
        return color;
    }

    @Override
    public String toString() {
        switch (color){
            case Color.BLACK:
                return "Black";
            case Color.BLUE:
                return "Blue";
            case Color.CYAN:
                return "Cyan";
            case Color.DKGRAY:
                return "Dark Gray";
            case Color.GRAY:
                return "Gray";
            case Color.GREEN:
                return "Green";
            case Color.LTGRAY:
                return "Light Gray";
            case Color.MAGENTA:
                return "Magenta";
            case Color.RED:
                return "Red";
            case Color.YELLOW:
                return "Yellow";
        }
        return "Unknown Color";
    }
    /**Used for the spinners to go from string to color*/
    public static MyColor unToString(String color){
        if(color.equals("Black")){
            return MyColor.BLACK;
        }
        if(color.equals("Blue")){
            return MyColor.BLUE;
        }
        if(color.equals("Cyan")){
            return MyColor.CYAN;
        }
        if(color.equals("Dark Gray")){
            return MyColor.DARKGREY;
        }
        if(color.equals("Gray")){
            return MyColor.GRAY;
        }
        if(color.equals("Green")){
            return MyColor.GREEN;
        }
        if(color.equals("LightGray")){
            return MyColor.LIGHTGRAY;
        }
        if(color.equals("Magenta")){
            return MyColor.MAGENTA;
        }
        if(color.equals("Red")){
            return MyColor.RED;
        }
        if(color.equals("Yellow")){
            return MyColor.YELLOW;
        }
        return null;
    }
}
