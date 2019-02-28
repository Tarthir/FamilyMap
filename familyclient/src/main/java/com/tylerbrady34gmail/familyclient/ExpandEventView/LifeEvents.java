package com.tylerbrady34gmail.familyclient.ExpandEventView;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

import models.Event;

/**
 * Created by tyler on 3/29/2017.
 * Holds a list of all our life events
 */

public class LifeEvents implements Parent<Event>{
    // a recipe contains several ingredients
    private List<Event> mEvents;
    /**The name of this family*/
    private String mName;

    public LifeEvents(String name, List<Event> people) {
        mEvents = people;
        mName = name;
    }

    @Override
    public List<Event> getChildList() {
        return mEvents;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return mName;
    }
}
