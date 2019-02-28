package com.tylerbrady34gmail.familyclient.Models;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.tylerbrady34gmail.familyclient.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tyler on 3/30/2017.
 * Holds useful functions for our Program
 */

public class Utils {

    /**Gets the right gender icon for the person activity and the map fragment for the imageViews
     * @param context  the current context
     * @param gender  the person's gender(Wouldn't want to assume it)
     * @return Drawable*/
    public static Drawable getGenderIcon(Context context,String gender) {
        if(gender.toLowerCase().contains("m")){
            return new IconDrawable(context, Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
        }
        else if(gender.toLowerCase().contains("f")){
            return new IconDrawable(context, Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
        }
        else {
            return new IconDrawable(context,Iconify.IconValue.fa_genderless).colorRes(R.color.genderless).sizeDp(40);
        }
    }

    /**Used to get the location icon
     * @param context the current context
     * @return Drawable*/
    public static Drawable getLocIcon(Context context){
        return new IconDrawable(context,Iconify.IconValue.fa_map_marker).colorRes(R.color.gray).sizeDp(40);

    }
    /**Used to get the go to the top button icon
     * @param context, the current context
     * @return  Drawable*/
    public static Drawable getToTopIcon(Context context){
        return new IconDrawable(context,Iconify.IconValue.fa_angle_double_up).colorRes(R.color.gray).sizeDp(30);
    }
    /**Puts together this nasty list of colors
     * @return ArrayList<BitmapDescriptor>*/
    static float[] getMarkerColors(){
        float[]  descriptor = new float[] {(BitmapDescriptorFactory.HUE_AZURE),
               (BitmapDescriptorFactory.HUE_BLUE), (BitmapDescriptorFactory.HUE_CYAN),
                (BitmapDescriptorFactory.HUE_GREEN), (BitmapDescriptorFactory.HUE_MAGENTA),
                (BitmapDescriptorFactory.HUE_ORANGE), (BitmapDescriptorFactory.HUE_RED),
                (BitmapDescriptorFactory.HUE_VIOLET), (BitmapDescriptorFactory.HUE_ROSE),
                (BitmapDescriptorFactory.HUE_YELLOW)};
        return descriptor;
    }
}
