package com.example.dayplanner;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EventListViewModel extends AndroidViewModel {

    private SavedStateHandle savedStateHandle;
    private MutableLiveData<List<Event>> events;
    private Application application;
    private static final String SAVED_EVENTS = "saved_events";
    private static final String PREFS = "shared_prefs";

    public EventListViewModel(Application appliction, SavedStateHandle stateHandle){
        super(appliction);
        this.application = appliction;
        savedStateHandle = stateHandle;
        this.events = new MutableLiveData<>();
        events.setValue(loadEvents());
        savedStateHandle.set("events", events.getValue());
    }


    /*
    public void setEvents(List<Event> events) {
        this.events.setValue(events);
    }*/
    public MutableLiveData<List<Event>> getEvents() {
        return savedStateHandle.getLiveData("events", new ArrayList<>());
    }

    public void addEvent(Event event){
        List<Event> eventList = getEvents().getValue();
        eventList.add(event);
        events.setValue(eventList);
        savedStateHandle.set("events", eventList);
    }

    private ArrayList<Event> loadEvents(){
        SharedPreferences preferences = application.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String eventsJSON = preferences.getString(SAVED_EVENTS, "");
        Type type = new TypeToken<ArrayList<Event>>(){}.getType();
        ArrayList<Event> events = gson.fromJson(eventsJSON, type);
        if(events == null){return new ArrayList<>();}
        return events;
    }
}
