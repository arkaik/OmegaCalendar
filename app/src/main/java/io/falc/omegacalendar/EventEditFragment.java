package io.falc.omegacalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by falc on 5/01/16.
 */
public class EventEditFragment extends DialogFragment {
    EditText evname, evdate1, evtime1, evdate2, evtime2;
    String[] sarg;
    MainActivity a;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long id = Long.getLong(sarg[0]);
                        a.getCalendarController().editEvent( id, evname.getText().toString(), evdate1.getText().toString(),
                                evtime1.getText().toString(), evdate2.getText().toString(), evtime2.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Editar calendario");*/

        return inflater.inflate(R.layout.content_event, null);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle arg = getArguments();

        a = (MainActivity) getActivity();


        evname = (EditText) a.findViewById(R.id.name_field);
        evdate1 = (EditText) a.findViewById(R.id.date1_field);
        evtime1 = (EditText) a.findViewById(R.id.time1_field);
        evdate2 = (EditText) a.findViewById(R.id.date2_field);
        evtime2 = (EditText) a.findViewById(R.id.time2_field);

        final String s = arg.getString("event");
        sarg = s != null ? s.split("[|]") : new String[0];

        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy", Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", Locale.getDefault());

        Date d1 = new Date(Long.parseLong(sarg[5]));
        String sd1 = sdf1.format(d1);
        String st1 = sdf2.format(d1);

        Date d2 = new Date(Long.parseLong(sarg[6]));
        String sd2 = sdf1.format(d2);
        String st2 = sdf2.format(d2);

        evname.setText(sarg[1]);
        evdate1.setText(sd1);
        evtime1.setText(st1);
        evdate2.setText(sd2);
        evtime2.setText(st2);

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save:
                Long id = Long.getLong(sarg[0]);
                a.getCalendarController().editEvent( id, evname.getText().toString(), evdate1.getText().toString(),
                        evtime1.getText().toString(), evdate2.getText().toString(), evtime2.getText().toString());
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
