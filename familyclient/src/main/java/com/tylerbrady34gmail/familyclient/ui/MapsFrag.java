package com.tylerbrady34gmail.familyclient.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.tylerbrady34gmail.familyclient.Models.Filter;
import com.tylerbrady34gmail.familyclient.Models.Model;
import com.tylerbrady34gmail.familyclient.Models.Utils;
import com.tylerbrady34gmail.familyclient.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import models.Event;
import models.Person;

import static java.lang.Double.parseDouble;


/**
 * A map fragment class
 */
public class MapsFrag extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {
    /**
     * Our google map
     */
    private GoogleMap mMap;
    /**
     * Our tetxview
     */
    private TextView mCurrTextView;
    /**
     * A list of our mPolylines on the screen
     */
    List<Polyline> mPolylines = new ArrayList<>();
    /**
     * Our gender image view
     */
    private ImageView mGenderImage;
    /**
     * The person clicked
     */
    private String mPersonClicked;
    /**
     * The current marker
     */
    private Marker mCurrMarker;
    /**
     * Key for getting the fathers side filter
     */
    private final String mFathersSide = "Father's Side";
    /**
     * Key for getting the mothers side filter
     */
    private final String mMothersSide = "Mother's Side";
    /**The event Passed in to this fragment by the personActivity*/
    private Event mEventParam;

    public MapsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MapsFrags", "Entering onCreate");
        setHasOptionsMenu(true);
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null) {//check to see if we are coming here from another activity o from login screen
            String eventID = (String) bundle.get("event_key");
            if(eventID != null) {
                mEventParam = Model.getEvents().get(eventID);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapsfrag);
        mCurrTextView = (TextView) view.findViewById(R.id.person_info);
        mCurrTextView.setText(R.string.show_begining_text);
        mGenderImage = (ImageView) view.findViewById(R.id.imageView);
        mapFragment.getMapAsync(this);
        return view;
    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMarkerClickListener(this);
        if (Model.getFilter().isFilterShowing(mFathersSide)) {//check if these filters are on or off
            addMapMarker(Model.getPaternalAncestors());
        }
        if (Model.getFilter().isFilterShowing(mMothersSide)) {
            addMapMarker(Model.getMaternalAncestors());
        }
    }


    /**
     * Puts in all the map markers on the GoogleMap
     *
     * @param people all the people being added to the map
     */
    private void addMapMarker(Set<String> people) {
        //for every person, grab their lat and long of each of their events and add it to the map
        for (String person : people) {
            if (Filter.getInstance().isGenderShowing(person)) {

                List<Event> eventList = Model.getPersnEvntMap().get(person);
                for (int i = 0; i < eventList.size(); i++) {//for each event

                    Event event = eventList.get(i);
                    if (Filter.getInstance().isEventShowing(event)) {//if its nota filtered event

                        double lat = parseDouble(event.getLatitude()), lng = parseDouble(event.getLongitude());
                        String city = event.getCity();
                        //add the marker
                        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title(city));
                        marker.setTag(event);
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(Model.getEventColors().get(event.getEventType())));
                        //if we are in the mapactivity and have been passed in an event to center on
                        if(mEventParam != null && event.equals(mEventParam)){
                            onMarkerClick(marker);
                            //updateViews(marker,event);//update our textviews
                        }
                    }
                }//for
            }//if
        }//for
    }
    /**Helper function which updates the textview/ImageView for the fragment
     * @param marker the current marker clicked on
     * @param event the current event associated with the marker
     * */
    void updateViews(Marker marker, Event event){
        Person personClicked = Model.getPeople().get(event.getPersonID());
        mPersonClicked = personClicked.getPersonID();//set these variables , used in OnResume()
        mCurrMarker = marker;
        String fName = personClicked.getfName();
        String lName = personClicked.getlName();

        mCurrTextView.setText(fName + " " + lName + "\n" + event.toString());
        mGenderImage.setImageDrawable(Utils.getGenderIcon(this.getContext(), personClicked.getGender()));
        doLines();//draws all the lines
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("MapFrag", "Entering onMarkerClick");
        clearLines();
        final Event event = (Event)marker.getTag();//get the associated event
        updateViews(marker,event);//updates the textviews
        //For when the textview associated with this marker is clicked
        mCurrTextView.setOnClickListener(new View.OnClickListener() {//set the click listener
            private Event currEvent = event;//grab the current event

            @Override
            public void onClick(View v) {
                Log.d("MapsActvity", "TextField has been clicked");
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                Bundle b = new Bundle();
                b.putString("person_key", currEvent.getPersonID()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                getActivity().startActivity(intent);
            }
        });
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        //But return true if we being passed in an event from the person activity
        if(mEventParam != null){
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()), 250, null);
            return true;
        }
        return false;
    }

    /**
     * A function which hands out the duties of updating the UI with lines
     */
    private void doLines() {
        if (Model.getSettings().isShowFamilyLines()) {
            doFamilyLines();
        }
        if (Model.getSettings().isShowLifeLines()) {
            doLifeLines();
        }
        if (Model.getSettings().isShowSpouseLines()) {
            doSpouseLines();
        }
    }

    /**
     * Draws the lines from the clicked to his parents to their parents and so on
     */
    private void doFamilyLines() {

        int lineWidth = 12;
        Event clickedOnEvent = (Event)mCurrMarker.getTag();

        if (Filter.getInstance().isShowingMales()) {
            String father = Model.getPeople().get(mPersonClicked).getFather();//the clicked persons dad
            linesHelperPerson(mPersonClicked, father, lineWidth, clickedOnEvent);
        }
        if (Filter.getInstance().isShowingFemales()){
            String mother = Model.getPeople().get(mPersonClicked).getMother();
            linesHelperPerson(mPersonClicked, mother, lineWidth, clickedOnEvent);
        }
    }

    /**
     * A recursive function to draw family lines
     *
     * @param person         , The person of the current generation
     * @param nextGenPerson  the mother/father of 'person'
     * @param lineWidth      the desired linewidth
     * @param clickedOnEvent if this is our first pass through this function, this param will hold the described event,else null
     */
    private void linesHelperPerson(String person, String nextGenPerson, int lineWidth, Event clickedOnEvent) {
        Event currEvent = clickedOnEvent, nextGenEvent = null;
        try {
            if (currEvent == null) {//checking to see if this value has already been fiiled by the clicked on event
                currEvent = nonFiltered(Model.getPersnEvntMap().get(person));
            }
        } catch (Exception e) {
            Log.d("LineDrawing", "No events for ID: \'" + person + "\'");
            Log.d("LineDrawing", e.getMessage());
        }
        try {
            //This will grab all the no
            nextGenEvent = nonFiltered(Model.getPersnEvntMap().get(nextGenPerson));
        } catch (Exception e) {
            Log.d("LineDrawing", "No events for ID:\'" + nextGenPerson + "\'");
            Log.d("LineDrawing", e.getMessage());
        }

        if (currEvent != null && nextGenEvent != null) {
            Log.d("LineDrawing", "Drawing a " + currEvent.getEventType() + " to a " + nextGenEvent.getEventType());
            LatLng pos = new LatLng(parseDouble(currEvent.getLatitude()), parseDouble(currEvent.getLongitude()));
            LatLng pos2 = new LatLng(parseDouble(nextGenEvent.getLatitude()), parseDouble(nextGenEvent.getLongitude()));
            //draw the line
            drawLines(pos, pos2, lineWidth, Model.getSettings().getFamilyColor().getColor());
        }
        //Get the next generation
        if (!nextGenPerson.equals("")) {
            lineWidth -= 3;
            if (lineWidth == 0) {
                lineWidth = 1;
            }
            getNextGen(nextGenPerson, lineWidth);
        }

    }

    /**
     * Gets the next generation ready for line drawing
     *
     * @param nextGenPerson the person whose parents we need to draw lines to
     * @param lineWidth     the desired line width
     */
    private void getNextGen(String nextGenPerson, int lineWidth) {
        //Use of this function in the recursion is to help make the above function not too big
        Log.d("GetNextGen", "Recursing");
        String newPerson = Model.getPeople().get(nextGenPerson).getPersonID();
        String newNextGenDad = Model.getPeople().get(nextGenPerson).getFather();//the next gen mother
        String newNextGenMom = Model.getPeople().get(nextGenPerson).getMother();//the next gen father
        if (Filter.getInstance().isShowingFemales() && !newNextGenMom.equals("")) {//if we have not hit the end of our recursion
            linesHelperPerson(newPerson, newNextGenMom, lineWidth, null);
        }
        if (Filter.getInstance().isShowingMales() && !newNextGenDad.equals("")) {
            linesHelperPerson(newPerson, newNextGenDad, lineWidth, null);
        }
    }

    /**
     * Holds the logic for drawing life lines. Meaning the events of a persons life
     */
    private void doLifeLines() {
        int lineWidth = 6;
        List<Event> events = Model.getPersnEvntMap().get(mPersonClicked);

        ArrayList<Integer> positions = nonFiltered(events, 0);
        for (int i = 0; i < positions.size() - 1; i++) {//go through all the non Filtered events and draw lines btwn them
            Event event = events.get(positions.get(i)), event2 = events.get(positions.get(i + 1));
            LatLng pos = new LatLng(parseDouble(event.getLatitude()), parseDouble(event.getLongitude()));
            LatLng pos2 = new LatLng(parseDouble(event2.getLatitude()), parseDouble(event2.getLongitude()));
            drawLines(pos, pos2, lineWidth, Model.getSettings().getLifeColor().getColor());
        }
    }

    /**
     * Grabs the index of the first non filtered event it sees based on the intial index given
     *
     * @param events the list of events to search through
     * @param index  the given index to start the search at
     * @return int
     */
    private ArrayList<Integer> nonFiltered(List<Event> events, int index) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = index; i < events.size(); i++) {
            if (Filter.getInstance().isEventShowing(events.get(i))) {
                list.add(i);
            }
        }
        return list;
    }

    /**
     * Grabs the index of the first non filtered event it sees
     *
     * @param events the list of events to search through
     * @return int
     */
    private Event nonFiltered(List<Event> events) {
        for (int i = 0; i < events.size(); i++) {
            if (Filter.getInstance().isEventShowing(events.get(i))) {
                return events.get(i);
            }
        }
        return null;
    }

    /**
     * Holds all the logic for doing Spouse lines, meaning a line i drawn from the clicked on to the spouses birth palce
     */
    private void doSpouseLines() {
        if(Filter.getInstance().isShowingMales() && Filter.getInstance().isShowingFemales()) {//only if possible to draw lines to spouses

            int lineWidth = 6;
            String spouse = Model.getPeople().get(mPersonClicked).getSpouse();
            if (spouse.equals("")) {
                return;
            }//if there is no spouse
            List<Event> events = Model.getPersnEvntMap().get(spouse);//get the events of the spouse
            for (Event event : events) {
                //if this event type is not filtered and check edge case of spouses being mother/father of user when one side of fam is not showing
                if (Filter.getInstance().isEventShowing(event) && !Filter.getInstance().isUserParentsEdgeCase(event)) {

                    LatLng pos = new LatLng(parseDouble(event.getLatitude()), parseDouble(event.getLongitude()));
                    drawLines(mCurrMarker.getPosition(), pos, lineWidth, Model.getSettings().getSpouseColor().getColor());
                    break;//we only want their first event
                }
            }
        }
    }

    /**
     * Clears the lines from the map when we are done with them
     */

    private void clearLines() {
        if (mPolylines.size() > 0) {
            for (Polyline line : mPolylines) {
                line.remove();
            }
        }
    }

    /**
     * Draws the lines on the map connecting people
     *
     * @param pos,      a Latlng Position
     * @param pos2      a Latlng position
     * @param lineWidth the width of the line being drawn
     * @param color     the color of line being drawn
     */
    private void drawLines(LatLng pos, LatLng pos2, int lineWidth, int color) {
        PolylineOptions line = new PolylineOptions().add(pos, pos2).width(lineWidth).color(color);
        mPolylines.add(mMap.addPolyline(line));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(getActivity() instanceof MainActivity) {
            inflater.inflate(R.menu.my_menu, menu);
            IconDrawable draw = new IconDrawable(getActivity(), Iconify.IconValue.fa_filter).colorRes(R.color.white).sizeDp(40);
            menu.getItem(1).setIcon(draw);//sets filter item to have the right icon
        }
        else{
            inflater.inflate(R.menu.my_upmenu, menu);
            IconDrawable draw = new IconDrawable(getActivity(), Iconify.IconValue.fa_angle_double_up).colorRes(R.color.white).sizeDp(40);
            menu.getItem(0).setIcon(draw);//sets filter item to have the right icon
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.filter:
                intent = new Intent(getActivity(), FilterActivity.class);
                startActivity(intent);
                return true;
            case R.id.toTopButton:
                intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        Log.d("MapsFrags", "Starting");
        super.onResume();
        if (mMap != null) {//if we are resuming after having already been to this fragment before
            Log.d("MapsFrags", "Checking settings and filters");
            checkSettingsAndFilters();
        }
    }

    /**
     * Clears the maps so that we can check the settings and filters again
     */
    private void checkSettingsAndFilters() {
        //clears the map and resets all views
        mMap.setMapType(Model.getMapType().getMapType());//sets the mapType to the right one
        mMap.clear();
        mPolylines.clear();
        mCurrTextView.setText(R.string.show_begining_text);
        mGenderImage.setImageDrawable(Utils.getGenderIcon(getContext(), ""));

        onMapReady(mMap);

    }

    @Override
    public void onStart() {
        Log.d("MapsFrags", "Starting");
        super.onStart();
    }


}
