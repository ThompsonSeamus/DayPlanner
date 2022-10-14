package com.example.dayplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EventListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String SAVED_EVENTS = "saved_events";
    private static final String PREFS = "shared_prefs";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventListFragment newInstance(int columnCount) {
        EventListFragment fragment = new EventListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            EventRecyclerViewAdapter adapter = new EventRecyclerViewAdapter(context);

            EventListViewModel eventListViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(EventListViewModel.class);
            eventListViewModel.getEvents().observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
                @Override
                public void onChanged(List<Event> events) {
                    adapter.setEvents(events);
                }
            });


            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    private ArrayList<Event> getEvents(){
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String eventsJSON = preferences.getString(SAVED_EVENTS, "");
        Type type = new TypeToken<ArrayList<Event>>(){}.getType();
        ArrayList<Event> events = gson.fromJson(eventsJSON, type);
        if(events == null){return new ArrayList<>();}
        return events;
    }
}