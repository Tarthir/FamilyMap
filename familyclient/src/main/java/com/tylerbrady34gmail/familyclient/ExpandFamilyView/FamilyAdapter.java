package com.tylerbrady34gmail.familyclient.ExpandFamilyView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.tylerbrady34gmail.familyclient.R;
import com.tylerbrady34gmail.familyclient.ui.SearchActivity;

import java.util.List;

import models.Person;

/**
 * Created by tyler on 3/28/2017.
 * Our adapter for the famil class
 */
public class FamilyAdapter extends ExpandableRecyclerAdapter<Family, Person, FamilyViewHolder, PersonViewHolder> {
    /**Our inflater variable*/
    private LayoutInflater mInflater;
    /**a check to let us know if we are coming from the person activity or search*/
    public static boolean isFromSearch = false;

    public FamilyAdapter(Context context, List<Family> familyList) {
        super(familyList);
        //Are we coming from SearchActivity?
        isFromSearch = context.getClass().equals(SearchActivity.class);
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public FamilyViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View familyView = mInflater.inflate(R.layout.family_view, parentViewGroup, false);
        return new FamilyViewHolder(familyView);
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View personView = mInflater.inflate(R.layout.person_view, childViewGroup, false);
        return new PersonViewHolder(personView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull FamilyViewHolder parentViewHolder, int parentPosition, @NonNull Family parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull PersonViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Person child) {
        childViewHolder.bind(child);
    }

}
