package io.falc.omegacalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by falc on 3/01/16.
 */
public class CalendarListFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        MainActivity ma = (MainActivity) getActivity();
        String[] data = ma.getCalendarController().getCalendarsData();
        String[] names = new String[data.length];
        final String[] id = new String[data.length];

        for (int i = 0; i < data.length; i++)
        {
            String[] args = data[i].split("[|]");
            names[i] = args[1];
            id[i] = args[0];
        }

        builder.setTitle("Elige un calendario")
                .setItems(names, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        Intent intent = new Intent(getActivity(),EventActivity.class);
                        intent.putExtra("calID", id[which]);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
