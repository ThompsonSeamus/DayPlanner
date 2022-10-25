package com.example.dayplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_EVENT_REQUEST_CODE = 1;
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_NAME = "event_name";
    private static final String CHANNEL_ID = "primary_notification_channel";
    private static final int NOTIF_ID = 0;

    private NotificationManager notificationManager;
    private EventListViewModel eventListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eventListViewModel = new ViewModelProvider(this).get(EventListViewModel.class);
        createNotificationChannel();
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
            sendNotification();
        }
        else{
            Toast.makeText(this, "Invalid Event", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notificationManager.notify(NOTIF_ID, notifyBuilder.build());
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIF_ID, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_calendar_today_24)
                .setContentTitle("Event Added")
                .setContentText("A new event has been added to MyEvents")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true);

        return builder;
    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}