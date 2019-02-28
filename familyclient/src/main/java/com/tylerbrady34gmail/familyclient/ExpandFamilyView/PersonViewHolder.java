package com.tylerbrady34gmail.familyclient.ExpandFamilyView;

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
import com.tylerbrady34gmail.familyclient.ui.PersonActivity;

import models.Person;

/**
 * Created by tyler on 3/29/2017.
 * A viewHolder for person's. THe parent of this class is FamilyViewHolder
 */

class PersonViewHolder extends ChildViewHolder {
    /**
     * My textView
     */
    private TextView mMyView;
    /**
     * The image View to the left of the textview
     */
    private ImageView mMyImageView;
    /**
     * A string for the female gender
     */
    private final String MALE = "m";
    /**
     * A string for the female gender
     */
    private final String FEMALE = "f";
    /**
     * This person
     */
    private Person mThisPerson;

    PersonViewHolder(View itemView) {
        super(itemView);
        mMyView = (TextView) itemView.findViewById(R.id.person_textView);
        mMyImageView = (ImageView) itemView.findViewById(R.id.person_view_image);
        final View mItemView = itemView;

        mMyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PersonViewHolder", "TextField has been clicked");
                Intent intent = new Intent(mItemView.getContext(), PersonActivity.class);
                Bundle b = new Bundle();
                b.putString("person_key", mThisPerson.getPersonID());
                intent.putExtras(b); //Put your id to your next Intent
                mItemView.getContext().startActivity(intent);
            }
        });
    }

    void bind(Person person) {
        //Use the below static boolean to see if we are coming from the search activity or not
        if (!FamilyAdapter.isFromSearch) {
            if (person == null) {//if there is no family to show
                Toast.makeText(mMyView.getContext(), "No Family to show for this person", Toast.LENGTH_SHORT).show();
                return;
            }
            if (person.getPersonID().equals(PersonActivity.clickedOn.getSpouse())) {//if the spouse
                String str = person.toString() + "\nSPOUSE";
                mMyView.setText(str);
            } else if (PersonActivity.clickedOn.getFather().equals(person.getPersonID())) {//if the father
                String str = person.toString() + "\nFATHER";
                mMyView.setText(str);
            } else if (PersonActivity.clickedOn.getMother().equals(person.getPersonID())) {
                String str = person.toString() + "\nMOTHER";
                mMyView.setText(str);
            } else if (person.getGender().equals("f")) {//if a kid
                String str = person.toString() + "\nDAUGHTER";
                mMyView.setText(str);
            } else {
                String str = person.toString() + "\nSON";
                mMyView.setText(str);
            }
        } else {
            if (person == null) {//if there is no people to show
                Toast.makeText(mMyView.getContext(), "No People to show for this search", Toast.LENGTH_SHORT).show();
                return;
            }
            mMyView.setText(person.toString());
        }

        //set the gender icons
        if (person.getGender().equals("f")) {
            mMyImageView.setImageDrawable(Utils.getGenderIcon(mMyImageView.getContext(), FEMALE));
        } else {
            mMyImageView.setImageDrawable(Utils.getGenderIcon(mMyImageView.getContext(), MALE));
        }
        mThisPerson = person;
    }
}
