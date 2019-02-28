package com.tylerbrady34gmail.familyclient.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tylerbrady34gmail.familyclient.FilterRecycler.FilterAdapter;
import com.tylerbrady34gmail.familyclient.FilterRecycler.FilterRows;
import com.tylerbrady34gmail.familyclient.Models.Filter;
import com.tylerbrady34gmail.familyclient.R;

import java.util.ArrayList;

/**
 * Created by tyler on 4/3/2017.
 * Our filter activity
 */

public class FilterActivity extends AppCompatActivity {
    private final String TAG = "FilterActivity";
    private RecyclerView mFilterRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"Entering FilterActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mFilterRecycler = (RecyclerView) findViewById(R.id.filter_recycler);
        ArrayList<FilterRows> filters = Filter.getInstance().getFilterRows();
        FilterAdapter filterAdapter = new FilterAdapter(this,filters);

        mFilterRecycler.setLayoutManager(new LinearLayoutManager(this));
        mFilterRecycler.setAdapter(filterAdapter);

    }

    @Override
    protected void onStart() {
        Log.d(TAG,"Starting FilterActivity");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"Stopping FilterActivity");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"Destroying FilterActivity");
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
