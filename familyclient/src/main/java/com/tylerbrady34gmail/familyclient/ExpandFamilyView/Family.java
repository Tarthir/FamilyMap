package com.tylerbrady34gmail.familyclient.ExpandFamilyView;

import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.List;

import models.Person;

/**
 * Created by tyler on 3/29/2017.
 * A class which holds a list of family members as a expandable recyclerview
 */

public class Family implements Parent<Person>{

    // a recipe contains several ingredients
    private List<Person> mPeople;
    /**The name of this family*/
    private String mName;

    public Family(String name, List<Person> people) {
        mPeople = people;
        mName = name;
    }

    @Override
    public List<Person> getChildList() {
        return mPeople;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }

    public String getName() {
        return mName;
    }
}
