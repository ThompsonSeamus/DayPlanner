package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.EventStats;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class AddEventActivity extends AppCompatActivity {

    private static final String PREFS = "shared_prefs";
    private static final String SAVED_EVENTS = "saved_events";
    private ArrayList<Event> events;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        preferences  = getSharedPreferences(PREFS, MODE_PRIVATE);
    }

    public void addEvent(View view) {
        EditText dateET = findViewById(R.id.date_edit_text);
        EditText nameET = findViewById(R.id.event_name_edit_text);

        Event event = new Event(dateET.getText().toString(), nameET.getText().toString());
        events = getEvents();
        events.add(event);
        saveEvents();

        finish();
    }

    public void cancel(View view){finish();}

    private void saveEvents(){
        Gson gson = new Gson();
        String eventsJSON = gson.toJson(events);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(SAVED_EVENTS, eventsJSON).commit();
    }

    private ArrayList<Event> getEvents(){
        Gson gson = new Gson();
        String eventsJSON = preferences.getString(SAVED_EVENTS, "");
        Type type = new TypeToken<ArrayList<Event>>(){}.getType();
        ArrayList<Event> events = gson.fromJson(eventsJSON, type);
        if(events == null){return new ArrayList<>();}
        return events;
    }



}