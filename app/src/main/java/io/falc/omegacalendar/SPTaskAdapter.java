package io.falc.omegacalendar;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by falc on 6/01/16.
 */
public class SPTaskAdapter extends ArrayAdapter<String> {

    private Activity context;
    String[] data = null;

    public SPTaskAdapter(Context context, int resource) {
        super(context, resource);
    }

    public SPTaskAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public SPTaskAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        this.data = objects;
    }

    public SPTaskAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public SPTaskAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    public SPTaskAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            //inflate your customlayout for the textview
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }
        //put the data in it
        String item = data[position];
        if(item != null)
        {
            TextView text1 = (TextView) row.findViewById(R.id.task_item_text);
            text1.setText(item);
        }

        return row;

    }
}

