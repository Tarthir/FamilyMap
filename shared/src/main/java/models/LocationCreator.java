package models;

import java.util.ArrayList;

import models.Location;

/**
 * Created by tyler on 2/24/2017.
 * Creates location objects
 */

public class LocationCreator {
    public LocationCreator() {
    }

    /**
     * Creates multiple people from input
     *
     * @PARAM Arraylist location data
     * @RETURN An arraylist of event objects
     */
    public Location createLocation(ArrayList<String> locData) {
        if (locData == null || locData.size() < 4 ) {
            return null;
        }
        else{
            return new Location(locData.get(0),locData.get(1),locData.get(2),locData.get(3));
        }
    }
}
