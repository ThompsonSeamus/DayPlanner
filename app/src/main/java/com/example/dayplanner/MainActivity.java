package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToAddEvent(View view){
        Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
        startActivity(intent);
    }

    public void addEvent(Event event){

    }
}