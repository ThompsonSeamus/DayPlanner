package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.usage.EventStats;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddEventActivity extends AppCompatActivity {

    private static final String PREFS = "shared_prefs";
    private static final String SAVED_EVENTS = "saved_events";
    private ArrayList<Event> events;
    SharedPreferences preferences;
    EditText dateET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        preferences  = getSharedPreferences(PREFS, MODE_PRIVATE);
        dateET.addTextChangedListener(new DateTextWatcher());
    }

    public void cancel(View view){finish();}

    public void addEvent(View view) {
        EditText nameET = findViewById(R.id.event_name_edit_text);

        Intent replyintent = new Intent();
        if(TextUtils.isEmpty(dateET.getText()) || TextUtils.isEmpty(nameET.getText())){
            setResult(RESULT_CANCELED, replyintent);
        }
        else{
            replyintent.putExtra(MainActivity.EVENT_DATE, dateET.getText().toString());
            replyintent.putExtra(MainActivity.EVENT_NAME, nameET.getText().toString());
            setResult(RESULT_OK, replyintent);
        }

        finish();
    }


    private class DateTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            int month;

            if(working.length() == 0){
                dateET.setError(null);
            }
            else if(working.length() == 1){
                try{
                    month = Integer.parseInt(working);
                    if(month > 1 && month < 10){
                        working = "0" + working + "/";
                        dateET.setText(working);
                        dateET.setSelection(working.length());
                    }
                }catch(NumberFormatException e){
                    isValid = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}