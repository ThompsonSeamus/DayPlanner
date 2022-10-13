package com.example.dayplanner;

public class Event {
    private String time;
    private String name;

    public Event(String time, String name) {
        this.time = time;
        this.name = name;
    }

    public void setDate(String time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return time;
    }

    public String getName() {
        return name;
    }
}
