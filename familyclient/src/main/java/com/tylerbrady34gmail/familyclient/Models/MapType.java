package com.tylerbrady34gmail.familyclient.Models;

import com.google.android.gms.maps.GoogleMap;

import java.util.Map;

/**
 * Created by tyler on 3/20/2017.
 * The types of maps for our app
 */

public enum MapType {
    Hybrid(GoogleMap.MAP_TYPE_HYBRID),
    Satellite(GoogleMap.MAP_TYPE_SATELLITE),
    Terrain(GoogleMap.MAP_TYPE_TERRAIN),
    Normal(GoogleMap.MAP_TYPE_NORMAL);
    /**The maptype*/
    private int mapType;

    MapType(int mapType){
        this.mapType = mapType;
    }

    public int getMapType() {
        return mapType;
    }

    /**Returns the int associated with a google map type
     * @param mapTypeString  the mapType in string form
     * @return a mapType as a int, or -1 if unknown string is passed in*/
    public static MapType unToString(String mapTypeString){
        if(mapTypeString.equals("Hybrid")){
            return MapType.Hybrid;
        }
        else if(mapTypeString.equals("Terrain")){
            return MapType.Terrain;
        }
        else if(mapTypeString.equals("Normal")){
            return MapType.Normal;
        }
        else {//default is satellite view
            return MapType.Satellite;
        }

    }
    @Override
    public String toString() {
        switch (mapType){
            case GoogleMap.MAP_TYPE_HYBRID:
                return "Hybrid";
            case GoogleMap.MAP_TYPE_NORMAL:
                return "Normal";
            case GoogleMap.MAP_TYPE_SATELLITE:
                return "Satellite";
            case GoogleMap.MAP_TYPE_TERRAIN:
                return "Terrain";
            default:
                return "Unknown MapType";
        }
    }
}
