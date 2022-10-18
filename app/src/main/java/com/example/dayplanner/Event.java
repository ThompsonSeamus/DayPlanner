package com.example.dayplanner;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String time;
    private String name;

    public Event(String time, String name) {
        this.time = time;
        this.name = name;
    }

    protected Event(Parcel in) {
        time = in.readString();
        name = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(name);
    }
}
