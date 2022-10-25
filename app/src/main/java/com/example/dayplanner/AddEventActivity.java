package com.example.dayplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.usage.EventStats;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {

    public static final String ERROR_MESSAGE = "Enter a valid date: MM/DD/YYYY";

    EditText dateET;
    TextInputLayout dateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        dateET = findViewById(R.id.date_edit_text);
        dateET.addTextChangedListener(new DateTextWatcher());

        dateLayout = findViewById(R.id.date_edit_text_layout);
        dateLayout.setEndIconOnClickListener(new EventDateListener());

    }


    public void cancel(View view) {
        finish();
    }

    public void addEvent(View view) {
        EditText nameET = findViewById(R.id.event_name_edit_text);

        Intent replyintent = new Intent();
        if (TextUtils.isEmpty(dateET.getText()) || TextUtils.isEmpty(nameET.getText())) {
            setResult(RESULT_CANCELED, replyintent);
        } else {
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
            int month, day, year;

            //length = 0
            if (working.length() == 0) {
                dateET.setError(null);
                //length = 1
            } else if (working.length() == 1) {
                try {
                    month = Integer.parseInt(working);
                    if (month > 1 && month < 10) {
                        working = "0" + working + "/";
                        dateET.setText(working);
                        dateET.setSelection(working.length());
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                }
                //length = 2
            } else if (working.length() == 2 && before == 0) {
                if (working.charAt(1) == '/') {
                    working = "0" + working.charAt(0);
                }
                try {
                    month = Integer.parseInt(working);
                    if (month < 1 || month > 12) {
                        working = "0" + working.charAt(0) + "/" + working.charAt(1);
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                }
                if (isValid) {
                    working += "/";
                    dateET.setText(working);
                    dateET.setSelection(working.length());
                }
                //length = 4
            } else if (working.length() == 4 && count < 4) {
                if (working.charAt(2) == '/' && working.charAt(3) == '/') {
                    working.substring(0, 3);
                    dateET.setText(working);
                    dateET.setSelection(working.length());
                } else {
                    try {
                        day = Integer.parseInt(working.substring(3));
                        if (day > 3) {
                            working = working.substring(0, 3) + "0" + working.charAt(3) + "/";
                        }
                        dateET.setText(working);
                        dateET.setSelection(working.length());
                    } catch (NumberFormatException e) {
                        isValid = false;
                    }
                }
                //length = 5
            } else if (working.length() == 5 && before == 0) {
                if (working.charAt(4) == '/') {
                    working = working.substring(0, 3) + "0" + working.charAt(3);
                }
                try {
                    month = Integer.parseInt(working.substring(0, 2));
                    day = Integer.parseInt(working.substring(3));
                    if (day <= 31) {
                        if (month == 2 && day > 28) {
                            isValid = false;
                        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day == 31) {
                            isValid = false;
                        }
                    } else {
                        isValid = false;
                    }
                } catch (NumberFormatException e) {
                    isValid = false;
                }
                if (isValid) {
                    working += "/";
                    dateET.setText(working);
                    dateET.setSelection(working.length());
                }
                //length = 7
            } else if (working.length() == 7) {
                if (working.charAt(5) == '/' && working.charAt(6) == '/') {
                    working = working.substring(0, 6);
                    dateET.setText(working);
                    dateET.setSelection(working.length());
                }
                //if length = 10
            } else if (working.length() == 10 && before == 0) {
                String enteredYear = working.substring(6);
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                try {
                    year = Integer.parseInt(enteredYear);
                    if (year < currentYear) isValid = false;
                } catch (NumberFormatException e) {
                    isValid = false;
                }
                if (isValid) {
                    dateET.setText(working);
                    dateET.setSelection(working.length());
                }
                //length is not 10
            } else if (working.length() != 10) {
                isValid = false;
            }
            //end of if
            if (!isValid) {
                dateET.setError(ERROR_MESSAGE);
            } else {
                dateET.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class EventDateListener implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
        GregorianCalendar calendar;

        @Override
        public void onClick(View v) {
            calendar = new GregorianCalendar();
            new DatePickerDialog(v.getContext(), this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        }


        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
            calendar.set(year, month, dayOfMonth);
            dateFormat.setCalendar(calendar);
            dateET.setText(dateFormat.format(calendar.getTime()));
            dateET.setError(null);
        }
    }
}

