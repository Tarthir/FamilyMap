package com.tylerbrady34gmail.familyclient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.tylerbrady34gmail.familyclient.ExpandEventView.LifeEvents;
import com.tylerbrady34gmail.familyclient.ExpandEventView.LifeEventsAdapter;
import com.tylerbrady34gmail.familyclient.ExpandFamilyView.Family;
import com.tylerbrady34gmail.familyclient.ExpandFamilyView.FamilyAdapter;
import com.tylerbrady34gmail.familyclient.Models.Filter;
import com.tylerbrady34gmail.familyclient.Models.Model;
import com.tylerbrady34gmail.familyclient.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import models.Event;
import models.Person;

/**
 * Created by tyler on 3/23/2017.
 * The Search activity
 */

public class SearchActivity extends AppCompatActivity{
    /**Our two recycler views*/
    private RecyclerView mPeopleRecycler,mEventRecycler;
    /**Our searchView widget*/
    private SearchView mSearchView;
    /**Our lists of people to search through*/
    private List<Person> mPeople = new LinkedList<>();
    /**Our list of events to search through*/
    private List<Event> mEvents = new LinkedList<>();

    public SearchActivity() {
    }

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupPrivateVars();
    }

    /**Used to setup my private data members*/
    private void setupPrivateVars() {
        mPeopleRecycler = (RecyclerView) findViewById(R.id.searchPeople_recycler);
        mEventRecycler = (RecyclerView) findViewById(R.id.searchEvent_recycler);
        mSearchView = (SearchView) findViewById(R.id.mySearchView);

        mSearchView.setQueryHint("Search for people and events");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    /**Does the search for our events and people
     * @param query the query string passed in*/
    private void doSearch(String query) {
        mPeople.clear();
        mEvents.clear();
        Set<String> peopleKeySet = Model.getPeople().keySet();
        Set<String> eventKeySet = Model.getEvents().keySet();
        for(String personID : peopleKeySet){
            //if the person's info is not filtered and contains the query
           if(personIsNotFiltered(personID) && personContainsQuery(personID,query)){
                mPeople.add(Model.getPeople().get(personID));
           }
        }
        for(String eventID : eventKeySet){
            String personID = Model.getEvents().get(eventID).getPersonID();
            //if the event is not filtered and contains the query.
            if(Filter.getInstance().isFilterShowing(Model.getEvents().get(eventID).getEventType())
                    && personIsNotFiltered(personID)
                    && eventContainsQuery(eventID,query)){

                mEvents.add(Model.getEvents().get(eventID));

            }
        }
        sendToRecyclerViews();
    }
    /**Checks if the person's toString contains the query
     * @param personID the personID of the person we are going to check
     * @param query the query
     * @return boolean*/
    private boolean personContainsQuery(String personID, String query) {
        String toString = Model.getPeople().get(personID).toString();
        return toString.toLowerCase().contains(query.toLowerCase());
    }

    /**Checks if the query is in the eventToString
     * @param eventID the event Id of the event we are going to check
     * @param query  the query we are going to check*/
    private boolean eventContainsQuery(String eventID, String query) {
        return Model.getEvents().get(eventID).toString().toLowerCase().contains(query.toLowerCase());
    }

    /**Sends off all our data to the recycler views*/
    private void sendToRecyclerViews() {
        //Get the RecyclerViews and Adapters ready
        List<LifeEvents> lifeList = new LinkedList<>(Collections.singletonList(new LifeEvents("EVENTS",mEvents)));
        LifeEventsAdapter lifeEventsAdapter = new LifeEventsAdapter(this,lifeList);
        lifeEventsAdapter.expandAllParents();
        mEventRecycler.setLayoutManager(new LinearLayoutManager(this));
        mEventRecycler.setAdapter(lifeEventsAdapter);

        List<Family> familyList = new LinkedList<>(Collections.singletonList(new Family("PEOPLE",mPeople)));
        FamilyAdapter familyAdapter = new FamilyAdapter(this, familyList);
        familyAdapter.expandAllParents();
        mPeopleRecycler.setLayoutManager(new LinearLayoutManager(this));
        mPeopleRecycler.setAdapter(familyAdapter);
        toastUser();
    }
    /**Used to give output to users*/
    private void toastUser() {
        String eventsStr = "", peopleStr = "";
        switch (mPeople.size()){
            case 1:
                peopleStr = "There was 1 person and ";
                break;
            case 0:
                peopleStr = "There were no people and ";
                break;
            default:
                peopleStr = "There were " + mPeople.size() + " people and ";
        }
        switch (mEvents.size()){
            case 1:
                eventsStr = "1 event produced by your search";
                break;
            case 0:
                eventsStr = "no events produced by your search";
                break;
            default:
                eventsStr = mEvents.size() + " events produced by your search";
        }
        Toast.makeText(this,peopleStr + eventsStr,Toast.LENGTH_SHORT).show();

    }

    /**Checks to see if this person is being filtered*/
    private boolean personIsNotFiltered(String personID){
        //check if this person's gender is showing and their side of the family is showing
        if( Filter.getInstance().isGenderShowing(personID)) {//if there gender is showing

            return (Model.getMaternalAncestors().contains(personID) && Filter.getInstance().checkMotherSide())
                    || (Model.getPaternalAncestors().contains(personID) && Filter.getInstance().checkFatherSide());
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean bool = super.onCreateOptionsMenu(menu);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        return bool;
    }
}
