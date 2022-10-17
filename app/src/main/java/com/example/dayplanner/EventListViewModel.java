package com.example.dayplanner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EventListViewModel extends ViewModel {

    private MutableLiveData<List<Event>> events;

    public EventListViewModel(){this.events = new MutableLiveData<>(new ArrayList<>());}

    /*
    public void setEvents(List<Event> events) {
        this.events.setValue(events);
    }
    */
    public MutableLiveData<List<Event>> getEvents() {
        return events;
    }

    public void addEvent(Event event){
        List<Event> eventList = events.getValue();
        eventList.add(event);
        events.setValue(eventList);
    }
}
