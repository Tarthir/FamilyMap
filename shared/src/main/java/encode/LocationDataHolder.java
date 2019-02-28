package encode;

import models.Location;

/**
 * Created by tyler on 3/2/2017.
 * Holds our Location array, needed to parse the locations.json file
 */

public class LocationDataHolder {
    /**An array of location objects*/
    private Location[] data;

    public LocationDataHolder(Location[] data) {
        this.data = data;
    }

    public Location[] getLocArray() {
        return data;
    }
}
