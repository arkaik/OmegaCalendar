package io.falc.omegacalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    CalendarController cc;
    EditText evname;
    EditText time1, time2;
    EditText date1, date2;
    Calendar myCalendar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();

        cc = new CalendarController(EventActivity.this);
        evname = (EditText) findViewById(R.id.name_field);
        time1 = (EditText) findViewById(R.id.time1_field);
        date1 = (EditText) findViewById(R.id.date1_field);
        time2 = (EditText) findViewById(R.id.time2_field);
        date2 = (EditText) findViewById(R.id.date2_field);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate1();
            }

        };

        final DatePickerDialog.OnDateSetListener datel2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate2();
            }

        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime1();
            }
        };

        final TimePickerDialog.OnTimeSetListener timel2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateTime2();
            }
        };

        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EventActivity.this,time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EventActivity.this, datel2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(EventActivity.this, timel2,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id = Long.parseLong(intent.getStringExtra("calID"));
                cc.addEvent(id, evname.getText().toString(), date1.getText().toString(), time1.getText().toString(),
                        date2.getText().toString(), time2.getText().toString());

                finish();

                /*
                Intent i = new Intent(EventActivity.this,MainActivity.class);
                startActivity(i);.edu
                */
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void updateDate1() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        date1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTime1()
    {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        time1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateDate2() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        date2.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTime2()
    {
        String myFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        time2.setText(sdf.format(myCalendar.getTime()));
    }

    private void setData()
    {

    }
}
