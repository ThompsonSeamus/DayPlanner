package com.example.dayplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_EVENT_REQUEST_CODE = 1;
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_NAME = "event_name";

    private EventListViewModel eventListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventListViewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(this)).get(EventListViewModel.class);
    }

    public void goToAddEvent(View view){
        Intent intent = new Intent(MainActivity.this, AddEventActivity.class);
        startActivityForResult(intent, ADD_EVENT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_EVENT_REQUEST_CODE && resultCode == RESULT_OK){
            Event event = new Event(data.getStringExtra(EVENT_DATE), data.getStringExtra(EVENT_NAME));
            eventListViewModel.addEvent(event);
        }
        else{
            Toast.makeText(this, "Invalid Event", Toast.LENGTH_SHORT).show();
        }
    }
}