package io.falc.omegacalendar;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class TaskListCursorAdapter extends SimpleCursorAdapter {

    public TaskListCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        CheckBox cb = (CheckBox) view.findViewById(R.id.taskcheckBox);
        TextView et = (TextView) view.findViewById(R.id.task_item_text);

        Integer bool = cursor.getInt(2);
        String name = cursor.getString(1);
        Boolean check = bool != 0;

        et.setText(name);
        cb.setChecked(check);
        cb.setText("");
    }
}
