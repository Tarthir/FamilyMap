package com.tylerbrady34gmail.familyclient.FilterRecycler;

/**
 * Created by tyler on 4/3/2017.
 * A special class to put filters in, which is used by the view holder
 */

public class FilterRows {
    /**The type of filter*/
    private String mType;
    /**Whether it is showing or not*/
    private boolean isShowing = true;

    public FilterRows(String typeOfFilter){
        mType = typeOfFilter;
    }

    public void toggleFilter(){
        this.isShowing = !this.isShowing;
    }

    public String getmType() {
        return mType;
    }

    public boolean isShowing() {
        return isShowing;
    }

}
