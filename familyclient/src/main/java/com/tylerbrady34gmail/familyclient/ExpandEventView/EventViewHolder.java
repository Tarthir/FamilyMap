package com.tylerbrady34gmail.familyclient.ExpandEventView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.tylerbrady34gmail.familyclient.Models.Utils;
import com.tylerbrady34gmail.familyclient.R;
import com.tylerbrady34gmail.familyclient.ui.MapsActivity;

import models.Event;

/**
 * Created by tyler on 3/29/2017.
 * Holds our views for the children of the expandable recyclerview
 */

class EventViewHolder extends ChildViewHolder<Event>{
    /**The textview for the event*/
    private TextView mMyView;
    /**The image view for the event*/
    private ImageView mImageView;
    /**The event bound to this view holder*/
    private Event mEvent;

    EventViewHolder(View itemView) {
        super(itemView);
        mMyView = (TextView) itemView.findViewById(R.id.event_textView);
        mImageView = (ImageView)  itemView.findViewById(R.id.marker_view_image);
        final View mItemView = itemView;

        mMyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//TODO FIX THIS
                Log.d("EventViewHolder","Event Field has been clicked");
                Intent intent = new Intent(mItemView.getContext(),MapsActivity.class);
                Bundle b = new Bundle();
                b.putString("event_key",mEvent.getEventID());
                intent.putExtras(b); //Put your id to your next Intent
                mItemView.getContext().startActivity(intent);
            }
        });
    }

    void bind(Event event) {
        if (event == null){//if there is no family to show
            Toast.makeText(mMyView.getContext(),"No Events to show for this person",Toast.LENGTH_LONG).show();
        }
        else {
            mMyView.setText(event.toString());
            mImageView.setImageDrawable(Utils.getLocIcon(mImageView.getContext()));
            mEvent = event;
        }
    }

}
