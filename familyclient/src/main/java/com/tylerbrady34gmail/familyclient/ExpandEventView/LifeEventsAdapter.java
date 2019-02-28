package com.tylerbrady34gmail.familyclient.ExpandEventView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.tylerbrady34gmail.familyclient.R;

import java.util.List;

import models.Event;

/**
 * Created by tyler on 3/29/2017.
 * Life events adapter, allows us to put our data into a recycler
 */

public class LifeEventsAdapter extends ExpandableRecyclerAdapter<LifeEvents, Event, LifeEventsViewHolder, EventViewHolder> {

    private LayoutInflater mInflater;

    public LifeEventsAdapter(Context context, List<LifeEvents> lifeEventsList) {
        super(lifeEventsList);
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LifeEventsViewHolder onCreateParentViewHolder(@NonNull ViewGroup parentViewGroup, int viewType) {
        View lifeView = mInflater.inflate(R.layout.life_event_view, parentViewGroup, false);
        return new LifeEventsViewHolder(lifeView);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateChildViewHolder(@NonNull ViewGroup childViewGroup, int viewType) {
        View eventView = mInflater.inflate(R.layout.event_view, childViewGroup, false);
        return new EventViewHolder(eventView);
    }

    @Override
    public void onBindParentViewHolder(@NonNull LifeEventsViewHolder parentViewHolder, int parentPosition, @NonNull LifeEvents parent) {
        parentViewHolder.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(@NonNull EventViewHolder childViewHolder, int parentPosition, int childPosition, @NonNull Event child) {
        childViewHolder.bind(child);
    }
}
