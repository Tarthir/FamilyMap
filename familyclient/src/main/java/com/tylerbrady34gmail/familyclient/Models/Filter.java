package com.tylerbrady34gmail.familyclient.Models;

import com.tylerbrady34gmail.familyclient.FilterRecycler.FilterRows;

import java.util.ArrayList;

import models.Event;

/**
 * Created by tyler on 3/20/2017.
 * Our class which holds our filters
 */

public class Filter {
    /**
     * Map of eventTypes and other filter settings and whether they are on or off
     */
    private ArrayList<FilterRows> filterRows;
    /**
     * Our Filter instance
     */
    private static Filter filter;


    private Filter() {
    }

    public static Filter getInstance() {
        if (filter == null) {
            filter = new Filter();
            return filter;
        }
        return filter;
    }

    /**
     * Sets up the Filter Rows associated with events
     */
    void setUpEventFilterRows() {
        filterRows = new ArrayList<>();
        for (String eventType : Model.getEventTypes()) {
            filterRows.add(new FilterRows(eventType));
        }
        filterRows.add(new FilterRows("Father's Side"));
        filterRows.add(new FilterRows("Mother's Side"));
        filterRows.add(new FilterRows("Show Males"));
        filterRows.add(new FilterRows("Show Females"));
    }
    /**Checks if the fathers Side of the family is showing
     * @return boolean*/
    public boolean checkFatherSide(){
        return filterRows.get(filterRows.size() - 4).isShowing();
    }
    /**Checks if the mothers side of the family is showing
     * @return boolean*/
    public boolean checkMotherSide(){
        return filterRows.get(filterRows.size() - 3).isShowing();
    }
    /**checks edge case of the spouse line where the Mother or Father's side are off
     * @param event  the given event in question
     * @return boolean*/
    public boolean isUserParentsEdgeCase(Event event){
        return  Model.getPersonChildren().get(event.getPersonID()).contains(Model.getUser())
                && (checkFatherSide() || checkMotherSide());
    }

    /**
     * Checks if a given event is filtered or not
     *
     * @param event the event given
     * @return boolean
     */
    public boolean isEventShowing(Event event) {
        return Filter.getInstance().isFilterShowing(event.getEventType());
    }

    /**
     * returns if gender is showing or not
     *
     * @param person the person whose gender we are checking
     * @return boolean
     */
    public boolean isGenderShowing(String person) {
        String gender = Model.getPeople().get(person).getGender();
        if (gender.equals("f")) {
            return isShowingFemales();
        } else if (gender.equals("m")) {
            return isShowingMales();
        }
        return true;
    }

    /**
     * Returns if females are showing
     *
     * @return boolean
     */
    public boolean isShowingFemales() {
        return filterRows.get(filterRows.size() - 1).isShowing();//this is because the female filter is the last one in the arraylist
    }

    /**
     * Returns if males are showing
     *
     * @return boolean
     */
    public boolean isShowingMales() {
        return filterRows.get(filterRows.size() - 2).isShowing();
    }

    /**
     * Used to find the FilterRow needed and return whether it is on or off
     *
     * @param filterType the name of the filter
     * @return boolean
     */
    public boolean isFilterShowing(String filterType) {
        for (FilterRows filter : filterRows) {
            if (filter.getmType().equals(filterType)) {
                return filter.isShowing();
            }
        }
        return false;
    }

    /**
     * Used to toggle the FilterRow specified to on or off
     *
     * @param filterName the name of the filter
     * @return void
     */
    public void toggleFilterRow(String filterName) {
        for (FilterRows filter : filterRows) {
            if (filter.getmType().equals(filterName)) {
                filter.toggleFilter();
                break;
            }
        }
    }

    public ArrayList<FilterRows> getFilterRows() {
        return filterRows;
    }
}
