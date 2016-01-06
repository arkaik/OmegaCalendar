package io.falc.omegacalendar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by falc on 1/01/16.
 */
public class CalendarNameFragment extends DialogFragment{

    public interface CalendarNameListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    CalendarNameListener mlistener;
    private EditText et;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        MainActivity a = (MainActivity) getActivity();
        mlistener = a.getCalendarController();

        View vi = inflater.inflate(R.layout.calendarname_dialog, null);

        et = (EditText) vi.findViewById(R.id.calName);

        builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mlistener.onDialogPositiveClick(CalendarNameFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mlistener.onDialogNegativeClick(CalendarNameFragment.this);
                    }
                })
                .setTitle("Nuevo calendario");

        return builder.create();
    }

    public String getCalendarName()
    {
        return et.getText().toString();
    }
}
