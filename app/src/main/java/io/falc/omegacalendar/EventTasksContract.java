package io.falc.omegacalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.CalendarContract;

/**
 * Created by falc on 5/01/16.
 */
public class EventTasksContract {

    public EventTasksContract() {}

    public static abstract class Tasks implements BaseColumns{
        public static final String TABLE_NAME = "task";
        public static final String EVENT_ID = "eventid";
        public static final String TASK_NAME = "taskname";
        public static final String TASK_DONE = "taskdone";

        public static final String CONTENT_URI = "io.falc.omegacalendar.android.contentproviders/tasks";
    }

}
