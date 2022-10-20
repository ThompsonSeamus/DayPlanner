package com.example.dayplanner;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dayplanner.databinding.FragmentEventListItemBinding;
import com.google.gson.Gson;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {

    private List<Event> mValues;
    private final Context context;
    private static final String SAVED_EVENTS = "saved_events";
    private static final String PREFS = "shared_prefs";

    public EventRecyclerViewAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentEventListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getDate());
        holder.mContentView.setText(mValues.get(position).getName());
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValues.remove(holder.mItem);
                setEvents(mValues);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues == null ? 0 : mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageButton deleteButton;
        public Event mItem;

        public ViewHolder(FragmentEventListItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.eventDate;
            mContentView = binding.eventName;
            deleteButton = binding.deleteButton;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void setEvents(List<Event> events){
        this.mValues = events;
        notifyDataSetChanged();
        saveEvents();
    }

    private void saveEvents(){
        SharedPreferences preferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String eventJSON = gson.toJson(mValues);
        preferences.edit().putString(SAVED_EVENTS, eventJSON).commit();

    }
}