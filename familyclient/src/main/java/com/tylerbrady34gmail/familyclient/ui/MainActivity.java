package com.tylerbrady34gmail.familyclient.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tylerbrady34gmail.familyclient.R;

public class MainActivity extends AppCompatActivity {
    public static boolean isLoggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_login);
        // “Create a new fragment transaction, include one add operation in it, and then commit it.”
        if(fragment == null){
            if(isLoggedIn) {
                fragment = new MapsFrag();
            }
            else {
                fragment = new LoginFragment();
            }
            fm.beginTransaction().add(R.id.fragment_spot,fragment).commit();
        }
    }

}
