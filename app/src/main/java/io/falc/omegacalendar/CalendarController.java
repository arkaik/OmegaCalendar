package io.falc.omegacalendar;

import android.Manifest;
import android.accounts.Account;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by falc on 1/01/16.
 */
public class CalendarController implements CalendarNameFragment.CalendarNameListener {

    public EventTasksDbHelper evtask;
    private SQLiteDatabase db;

    //Comprueba si la versión de Android es menor a API 16
    private static final boolean BEFORE_JELLYBEAN = android.os.Build.VERSION.SDK_INT < 16;

    public static final String ACCOUNT_NAME = "account@local.io";
    public static final String ACCOUNT_TYPE = BEFORE_JELLYBEAN ? "io.falc.omegacalendar.account" : CalendarContract.ACCOUNT_TYPE_LOCAL;
    public static final String CONTENT_AUTHORITY = "com.android.calendar";

    public static final Account account = new Account(ACCOUNT_NAME, ACCOUNT_TYPE);

    // Variables para construir el nombre del calendario
    private static final String DEFAULT_CALENDAR_NAME = "Calendar";
    private int CALENDAR_NAME_INT = 1;

    private AppCompatActivity activity;

    public CalendarController(AppCompatActivity act) {
        activity = act;
        evtask = new EventTasksDbHelper(activity.getBaseContext());
    }

    public void addCalendar(String name) {
        Random rand = new Random();
        int color = rand.nextInt(0xffffff);
        color = 0xff000000 + color;

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_NAME);
        cv.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
        cv.put(CalendarContract.Calendars.NAME, name);
        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, name);
        cv.put(CalendarContract.Calendars.CALENDAR_COLOR, color);
        cv.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        cv.put(CalendarContract.Calendars.OWNER_ACCOUNT, ACCOUNT_NAME);
        cv.put(CalendarContract.Calendars.VISIBLE, 1);
        cv.put(CalendarContract.Calendars.SYNC_EVENTS, 1);

        Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();

        builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL)
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .build();

        /*if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }*/


        Uri result = activity.getContentResolver().insert(builder.build(), cv);

    }

    public void editCalendar(long id, String name) {
        Random rand = new Random();
        int color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, name);
        cv.put(CalendarContract.Calendars.CALENDAR_COLOR, color);

        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, id);
        int rows = activity.getContentResolver().update(updateUri, cv, null, null);
        Log.i("CalendarController", "Rows updated: " + rows);

        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new Fragment2())
                .commit();

    }

    public void deleteCalendar(long id) {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, id);

        int rows = activity.getContentResolver().delete(deleteUri, null, null);
        Log.i("CalendarController", "Rows deleted: " + rows);

    }

    public String[] getCalendarsData() {
        String[] projection = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT,                 // 3
                CalendarContract.Calendars.CALENDAR_COLOR                 // 4
        };

        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_ACCOUNT_NAME_INDEX = 1;
        int PROJECTION_DISPLAY_NAME_INDEX = 2;
        int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
        int PROJECTION_CALENDAR_COLOR_INDEX = 4;

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Cursor cur = activity.getContentResolver().query(calendarUri, projection, null, null, null);
        String[] cjt = null;
        if (cur != null && cur.getCount() > 0) {
            cur.moveToFirst();

            String s_acc, s_cdn, s_own;
            long calID;
            int i_color;

            int num = cur.getCount();
            cjt = new String[num];

            int i = 0;
            do {
                calID = cur.getLong(PROJECTION_ID_INDEX);
                s_cdn = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
                i_color = cur.getInt(PROJECTION_CALENDAR_COLOR_INDEX);

                cjt[i] = calID + "|" + s_cdn + "|" + Integer.toString(i_color);
                ++i;

            } while (cur.moveToNext());

            cur.close();

        }
        return cjt;
    }

    public void addEvent(long calID, String name, String sd1, String st1, String ed1, String et1) {

        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm", Locale.getDefault());

        long t1 = 0;
        long t2 = 0;

        try {
            Date d1 = sdf.parse(sd1+" "+st1);
            gc.setTime(d1);
            t1 = gc.getTimeInMillis();


            Date d2 = sdf.parse(ed1+" "+et1);
            gc.setTime(d2);
            t2 = gc.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ContentResolver cr = activity.getContentResolver();

        ContentValues values = new ContentValues();
        Log.i("cc", CalendarContract.Events.CALENDAR_ID);
        values.put(CalendarContract.Events.CALENDAR_ID, calID);
        Log.i("cc", CalendarContract.Events.TITLE);
        values.put(CalendarContract.Events.TITLE, name);
        values.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
        values.put(CalendarContract.Events.DTSTART, t1);
        values.put(CalendarContract.Events.DTEND, t2);

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());
        Log.i("event", Long.toString(eventID));

    }

    public void editEvent(long evID, String name, String sd1, String st1, String ed1, String et1)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm",new Locale("es", "ES"));
        long st = 0;
        long en = 0;
        try {
            Date d1 = sdf.parse(sd1+" "+st1);
            st = d1.getTime();

            Date d2 = sdf.parse(ed1+" "+et1);
            en = d2.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ContentValues cv = new ContentValues();
        cv.put(CalendarContract.Events.TITLE, name);
        cv.put(CalendarContract.Events.DTSTART, st);
        cv.put(CalendarContract.Events.DTEND, en);

        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, evID);
        int rows = activity.getContentResolver().update(updateUri, cv, null, null);
        Log.i("CalendarController", "Rows updated: " + rows);

        activity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new Fragment3())
                .commit();
    }

    public void deleteEvent(long id)
    {
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);

        int rows = activity.getContentResolver().delete(deleteUri, null, null);
        Log.i("CalendarController", "Rows deleted: " + rows);

    }

    public String[] getEventsData() {
        String[] projection = new String[]{
                CalendarContract.Events._ID,                    // 0
                CalendarContract.Events.TITLE,                  // 1
                CalendarContract.Events.DESCRIPTION,            // 2
                CalendarContract.Events.CALENDAR_DISPLAY_NAME,  // 3
                CalendarContract.Events.EVENT_COLOR,            // 4
                CalendarContract.Events.DTSTART,                // 5
                CalendarContract.Events.DTEND                   // 6
        };

        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_TITLE_INDEX = 1;
        int PROJECTION_DESCRIPTION_INDEX = 2;
        int PROJECTION_CALENDAR_NAME_INDEX = 3;
        int PROJECTION_COLOR_INDEX = 4;
        int PROJECTION_DTSTART_INDEX = 5;
        int PROJECTION_DTEND_INDEX = 6;

        Uri eventUri = CalendarContract.Events.CONTENT_URI;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        Cursor cur = activity.getContentResolver().query(eventUri, projection, null, null, null);

        String[] cjt = null;

        if (cur != null && cur.getCount() > 0) {
            cur.moveToFirst();

            String s_tit, s_des, s_nam, s_dts, s_dte;
            long evID;
            int i_color;

            int num = cur.getCount();
            cjt = new String[num];

            int i = 0;
            do {
                evID = cur.getLong(PROJECTION_ID_INDEX);
                s_tit = cur.getString(PROJECTION_TITLE_INDEX);
                s_des = cur.getString(PROJECTION_DESCRIPTION_INDEX);
                s_nam = cur.getString(PROJECTION_CALENDAR_NAME_INDEX);
                i_color = cur.getInt(PROJECTION_COLOR_INDEX);
                s_dts = cur.getString(PROJECTION_DTSTART_INDEX);
                s_dte = cur.getString(PROJECTION_DTEND_INDEX);

                cjt[i] = evID + "|" + s_tit + "|" + s_des + "|" + s_nam + "|" + Integer.toString(i_color) + "|" + s_dts + "|" + s_dte;
                ++i;

            } while (cur.moveToNext());

            cur.close();

        }
        return cjt;
    }

    public String[] getInstancesInRange(long start, long end)
    {
        String[] INSTANCE_PROJECTION = new String[] {
                CalendarContract.Instances.EVENT_ID,      // 0
                CalendarContract.Instances.BEGIN,         // 1
                CalendarContract.Instances.END,           // 2
                CalendarContract.Instances.TITLE          // 3
        };

        // The indices for the projection array above.
        final int PROJECTION_ID_INDEX = 0;
        final int PROJECTION_BEGIN_INDEX = 1;
        final int PROJECTION_END_INDEX = 2;
        final int PROJECTION_TITLE_INDEX = 3;

        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, start);
        ContentUris.appendId(builder, end);

        ContentResolver cr = activity.getContentResolver();

        Cursor cur = cr.query(builder.build(), INSTANCE_PROJECTION, null, null, null);

        String[] data = null;

        if (cur != null && cur.getCount() > 0)
        {
            cur.moveToFirst();

            int s_evid;
            long s_start, s_end;
            String s_title;

            int num = cur.getCount();
            data = new String[num];

            int i = 0;
            do {
                s_evid = cur.getInt(PROJECTION_ID_INDEX);
                s_start = cur.getLong(PROJECTION_BEGIN_INDEX);
                s_end = cur.getLong(PROJECTION_END_INDEX);
                s_title = cur.getString(PROJECTION_TITLE_INDEX);

                data[i] = s_evid + "|" + s_start + "|" + s_end+"|"+s_title;
                i++;

            } while (cur.moveToNext());

            cur.close();

        }

        return data;
    }

    public void addTask(long evID, String name)
    {
        db = evtask.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EventTasksContract.Tasks.EVENT_ID, evID);
        cv.put(EventTasksContract.Tasks.TASK_NAME, name);
        cv.put(EventTasksContract.Tasks.TASK_DONE, false);

        long rows = db.insert(EventTasksContract.Tasks.TABLE_NAME, "null", cv);
        Log.i("AddTask", Long.toString(rows));
    }

    public Cursor getTasksOfAnEvent(long evID)
    {
        db = evtask.getWritableDatabase();

        String[] projection = {
                EventTasksContract.Tasks._ID,
                EventTasksContract.Tasks.TASK_NAME,
                EventTasksContract.Tasks.TASK_DONE,
                EventTasksContract.Tasks.EVENT_ID
        };

        int PROJECTION_ID_INDEX = 0;
        int PROJECTION_NAME_INDEX = 1;
        int PROJECTION_DONE_INDEX = 2;
        int PROJECTION_EVENT_INDEX = 3;

        String selection = EventTasksContract.Tasks.EVENT_ID + " = ?";
        String[] args = new String[] {Long.toString(evID)};

        return db.query(EventTasksContract.Tasks.TABLE_NAME,projection,selection,args,null,null,null);
    }

    public void showCalendarNameDialog()
    {
        CalendarNameFragment dialog = new CalendarNameFragment();

        dialog.show(activity.getSupportFragmentManager(), "CalendarNameFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

        CalendarNameFragment cnf = (CalendarNameFragment) dialog;
        String cname = cnf.getCalendarName();
        Log.i("CC", cname);
        if (cname.equals(""))
        {
            cname = DEFAULT_CALENDAR_NAME+" "+CALENDAR_NAME_INT;
            CALENDAR_NAME_INT++;
        }
        Log.i("CC", cname);
        addCalendar(cname);

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.getDialog().cancel();

    }

}
