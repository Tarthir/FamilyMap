package com.tylerbrady34gmail.familyclient.ExpandEventView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.tylerbrady34gmail.familyclient.R;

/**
 * Created by tyler on 3/29/2017.
 * Holds the views of our parent objects for our recycler view
 */

class LifeEventsViewHolder extends ParentViewHolder{
    /**A Tag for logs*/
    private final String TAG = "LifeEventsViewHolder";
    /**My arrow image*/
    private ImageView mArrowExpandImageView;
    /**Curr event*/

    private TextView mMyView;
    LifeEventsViewHolder(View itemView) {
        super(itemView);
        mMyView = (TextView) itemView.findViewById(R.id.life_textView);

        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow_expand_imageview);
        mArrowExpandImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapseView();
                } else {
                    expandView();
                }
            }
        });
    }
    @Override
    public boolean shouldItemViewClickToggleExpansion() {
        return false;
    }

    void bind(LifeEvents life) {
        mMyView.setText(life.getName());
    }

    @Override
    public boolean isExpanded() {
        return super.isExpanded();
    }

    @Override
    public void onClick(View v) {
        Log.d("TAG","Clicked");
        super.onClick(v);
    }

    @Override
    protected void expandView() {
        Log.d(TAG,"View expanded");
        super.expandView();
    }

    @Override
    protected void collapseView() {
        Log.d(TAG,"View collapsed");
        super.collapseView();
    }
}
