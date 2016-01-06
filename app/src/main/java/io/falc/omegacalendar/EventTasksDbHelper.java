package io.falc.omegacalendar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by falc on 5/01/16.
 */
public class EventTasksDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventTasksContract.Tasks.TABLE_NAME + " (" +
                    EventTasksContract.Tasks._ID + " INTEGER PRIMARY KEY," +
                    EventTasksContract.Tasks.TASK_NAME + TEXT_TYPE + COMMA_SEP +
                    EventTasksContract.Tasks.TASK_DONE + INTEGER_TYPE + COMMA_SEP +
                    EventTasksContract.Tasks.EVENT_ID + INTEGER_TYPE+" )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventTasksContract.Tasks.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Tasks.db";

    public EventTasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}