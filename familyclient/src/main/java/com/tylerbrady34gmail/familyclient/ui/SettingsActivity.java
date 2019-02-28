package com.tylerbrady34gmail.familyclient.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.tylerbrady34gmail.familyclient.Models.FamilyMapServerProxy;
import com.tylerbrady34gmail.familyclient.Models.MapType;
import com.tylerbrady34gmail.familyclient.Models.Model;
import com.tylerbrady34gmail.familyclient.Models.MyColor;
import com.tylerbrady34gmail.familyclient.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import infoObjects.EventsRequest;
import infoObjects.EventsResult;
import infoObjects.PeopleRequest;
import infoObjects.PeopleResult;

/**
 * Created by tyler on 3/23/2017.
 * Our settings activity. This handles all the settings we can toggle
 */

public class SettingsActivity extends AppCompatActivity {
    private final String TAG = "SettingsAct";
    private final String GET_DATA = "Resync";
    private TextView mLifeStoryTextView;
    private TextView mFamilyTreeTextView;
    private TextView mSpouseTextView;
    private TextView mMapTypeTextView;
    private TextView mResynctextView;
    private TextView mLogoutTextView;
    private Spinner mLifeSpinner;
    private Spinner mFamilySpinner;
    private Spinner mSpouseSpinner;
    private Spinner mMapSpinner;
    private Switch mLifeSwitch;
    private Switch mFamilySwitch;
    private Switch mSpouseSwitch;
    /**The list of currently used Colors*/
    private ArrayList<MyColor> myColors = new ArrayList<>();
    /**
     * Context
     */
    Context mContext = this;
    public SettingsActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Creating Settings Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        intialize();
    }

    private void intialize() {
        mLifeStoryTextView = (TextView) findViewById(R.id.lifestory_textView);
        mFamilyTreeTextView = (TextView) findViewById(R.id.familyTree_textView);
        mSpouseTextView = (TextView) findViewById(R.id.spouse_textView);
        mMapTypeTextView = (TextView) findViewById(R.id.mapType_textView);
        mResynctextView = (TextView) findViewById(R.id.resync_textView);
        mLogoutTextView = (TextView) findViewById(R.id.logout_textview);
        mLifeSpinner = (Spinner) findViewById(R.id.lifestory_spinner);
        mFamilySpinner = (Spinner) findViewById(R.id.familyTree_spinner);
        mSpouseSpinner = (Spinner) findViewById(R.id.spouse_spinner);
        mMapSpinner = (Spinner) findViewById(R.id.mapType_spinner);
        mLifeSwitch = (Switch) findViewById(R.id.lifestory_switch);
        mFamilySwitch = (Switch) findViewById(R.id.familyTree_switch);
        mSpouseSwitch = (Switch) findViewById(R.id.spouse_switch);
        setupTextViews();
        setupSwitches();
        setupSpinners();
    }

    /**
     * sets up our spinners
     */
    private void setupSpinners() {
        addItemsOnSpinners(mSpouseSpinner);
        addItemsOnSpinners(mFamilySpinner);
        addItemsOnSpinners(mLifeSpinner);
        List<String> list = new ArrayList<>();
        list.add(Model.getMapType().toString());//the current type is the first

        for(MapType mapType : MapType.values()){
            if(!mapType.equals(Model.getMapType())){
                list.add(mapType.toString());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMapSpinner.setAdapter(dataAdapter);
        setupOnItemSelectedListeners();
    }



    /**add items into spinner dynamically
     * @param spinner  the given spinner object
     * */
    public void addItemsOnSpinners(Spinner spinner) {
        List<String> list = new ArrayList<>();
        String currentColor = "";
        //grab the first color
        if(spinner.equals(mFamilySpinner)){list.add(currentColor = Model.getSettings().getFamilyColor().toString());}
        else if(spinner.equals(mSpouseSpinner)){list.add(currentColor = Model.getSettings().getSpouseColor().toString());}
        else{list.add(currentColor = Model.getSettings().getLifeColor().toString());}
        myColors.add(MyColor.unToString(currentColor));//these are the currently used Colors
        for(MyColor color : MyColor.values()){
            if(colorIsNotUsed(color)){
                list.add(color.toString());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    /**Checks to see if a color is not already selected by the user for another line
     * @param color  the color in question
     * @return boolean
     * */
    public boolean colorIsNotUsed(MyColor color){
        return (!color.equals(Model.getSettings().getLifeColor())) && (!myColors.contains(color));
    }

    /**
     * sets up our switches with their listeners
     */
    private void setupSwitches() {
        mLifeSwitch.setChecked(Model.getSettings().isShowLifeLines());
        mLifeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getSettings().toggleShowLifeLines();
            }
        });
        mSpouseSwitch.setChecked(Model.getSettings().isShowSpouseLines());
        mSpouseSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getSettings().toggleShowSpouseLines();
            }
        });
        mFamilySwitch.setChecked(Model.getSettings().isShowFamilyLines());
        mFamilySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.getSettings().toggleShowFamilyLines();
            }
        });

    }

    /**
     * Gives our TextViews their text
     */
    private void setupTextViews() {
        mLifeStoryTextView.setText(R.string.lifeText);
        mFamilyTreeTextView.setText(R.string.familyText);
        mSpouseTextView.setText(R.string.spouseText);
        mMapTypeTextView.setText(R.string.maptypeText);
        mResynctextView.setText(R.string.rsycnText);
        mLogoutTextView.setText(R.string.logout);
        //setup resync
        mResynctextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new httpTaskGetData().start();
            }
        });
        //Setup going back to the login screen
        mLogoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Logged Out!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                MainActivity.isLoggedIn = false;
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Starting SettingsActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping SettingsActivity");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying SettingsActivity");
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

    /**
     * An ASync task to grab the user data from the database
     */
    public class httpTaskGetData extends AsyncTask<URL, Integer, Object> {//URL im sending off

        void start() {
            Log.d(GET_DATA, "Do a regRequest");
            try {
                execute(new URL("http://" + Model.getIPAdress() + ":" + Model.getPortNum() + "/person"),
                        new URL("http://" + Model.getIPAdress() + ":" + Model.getPortNum() + "/event"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        protected Object doInBackground(URL... urls) {//... says treat it like an array even tho it isnt
            Log.d(GET_DATA, "Entering DoInBackGround");
            FamilyMapServerProxy proxy = new FamilyMapServerProxy();
            ArrayList<Object> result = new ArrayList<>();
            PeopleResult pResult = proxy.getPeople(urls[0], new PeopleRequest(""));
            EventsResult eResult = proxy.getEvents(urls[1], new EventsRequest(""));
            if (eResult.getMessage() == null || pResult.getMessage() == null) {
                Log.d(GET_DATA, "Data gathering succeeded");
                result.add(pResult);
                result.add(eResult);
                return result;
            }
            return null;

        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d(GET_DATA, "Entering onPostExecute");
            ArrayList<Object> result;

            try {
                result = (ArrayList<Object>) o;
                Model.setValues(result);
                super.onPostExecute(result);
            } catch (Exception e) {
                Log.d(GET_DATA, "Wrong type given...exiting...exception thrown");
                Toast.makeText(mContext, "Getting Data Failed", Toast.LENGTH_SHORT).show();
                return;
            }
            Log.d(GET_DATA, "Successful data get");
            String success_toast = "Hello " + Model.getUser().getfName() + " " + Model.getUser().getlName() + "!\n Your Data is Re-Sycned";
            Toast.makeText(mContext, success_toast, Toast.LENGTH_SHORT).show();
        }

    }
    /**Sets up the on click listeners for the spinners*/
    private void setupOnItemSelectedListeners() {
        mMapSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.setMapType(MapType.unToString((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpouseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getSettings().setSpouseColor(MyColor.unToString((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mFamilySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getSettings().setFamilyColor(MyColor.unToString((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mLifeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Model.getSettings().setLifeColor(MyColor.unToString((String)parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
