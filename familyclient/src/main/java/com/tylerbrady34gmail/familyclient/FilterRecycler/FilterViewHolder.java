package com.tylerbrady34gmail.familyclient.FilterRecycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tylerbrady34gmail.familyclient.Models.Filter;
import com.tylerbrady34gmail.familyclient.R;

/**
 * Created by tyler on 4/3/2017.
 * Our View Holder
 */

class FilterViewHolder extends RecyclerView.ViewHolder {

    private TextView mFilter;
    private Switch mSwitch;

    FilterViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.filter_view, parent, false));
        mFilter = (TextView) itemView.findViewById(R.id.filter_textView);
        mSwitch = (Switch) itemView.findViewById(R.id.filter_switch);
        mSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//set the on click listener for the on/off toggle
                Filter.getInstance().toggleFilterRow((String)mFilter.getText());
                if(mSwitch.isChecked()){
                    String on = ((String)mFilter.getText()).toUpperCase() + " Events have been switched On";
                    Toast.makeText(v.getContext(),on,Toast.LENGTH_SHORT).show();
                }
                else{
                    String off = ((String) mFilter.getText()).toUpperCase() + " Events have been switched Off";
                    Toast.makeText(v.getContext(),off,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void bind(FilterRows row) {
        mFilter.setText(row.getmType());
        mSwitch.setChecked(row.isShowing());
    }
}
