package com.example.dayplanner;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class EventListViewModel extends ViewModel {
    private MutableLiveData<List<Event>> events;

    public void setEvents(MutableLiveData<List<Event>> events) {
        this.events = events;
    }

    public MutableLiveData<List<Event>> getEvents() {
        return events == null ? new MutableLiveData<>() : events;
    }
}
