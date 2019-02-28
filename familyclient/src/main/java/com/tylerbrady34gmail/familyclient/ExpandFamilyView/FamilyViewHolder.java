package com.tylerbrady34gmail.familyclient.ExpandFamilyView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.tylerbrady34gmail.familyclient.R;

/**
 * Created by tyler on 3/28/2017.
 * View holder for the parent viewHolder, Family whose child is a list of Person objects
 */

class FamilyViewHolder extends ParentViewHolder {
    private final String TAG = "FamilyViewHolder";
    private ImageView mArrowExpandImageView;

    private TextView mMyView;
    FamilyViewHolder(View itemView) {
        super(itemView);
        mMyView = (TextView) itemView.findViewById(R.id.family_textView);

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

    public void bind(Family family) {
        mMyView.setText(family.getName());
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
