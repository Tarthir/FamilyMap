package models;

/**
 * Created by tyler on 2/10/2017.
 * This holds the information of locations in the world related to events of a users ancestors
 */

public class Location {
    /**The city of this location*/
    private String city;
    /**The country of this location*/
    private String country;
    /**The latitude this event took place at*/
    private String latitude;
    /**The longitude this event occured at*/
    private String longitude;

    public Location(String latitude, String longitude,String city, String country) {
        this.city = city;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }


    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if(this.getClass() != o.getClass()){return false;}
        Location other = (Location)o;
        if(!other.getCity().equals(this.getCity())){return false;}
        if(!other.getCountry().equals(this.getCountry())){return false;}
        if(!other.getLatitude().equals(this.getLatitude())){return false;}
        if(!other.getLongitude().equals(this.getLongitude())){return false;}
        return true;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
