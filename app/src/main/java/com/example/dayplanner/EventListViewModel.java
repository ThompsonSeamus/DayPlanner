package com.example.dayplanner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListViewModel extends ViewModel {

    private SavedStateHandle savedStateHandle;
    private MutableLiveData<List<Event>> events;

    public EventListViewModel(SavedStateHandle stateHandle){
        savedStateHandle = stateHandle;
        this.events = savedStateHandle.getLiveData("events", new ArrayList<>());
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
}
