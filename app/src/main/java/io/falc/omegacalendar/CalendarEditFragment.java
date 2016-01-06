package io.falc.omegacalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by falc on 5/01/16.
 */
public class CalendarEditFragment extends DialogFragment {
    private EditText et;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final Bundle arg = getArguments();

        final MainActivity a = (MainActivity) getActivity();

        View vi = inflater.inflate(R.layout.calendarname_dialog, null);

        et = (EditText) vi.findViewById(R.id.calName);

        builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long id = arg.getLong("calendar");
                        a.getCalendarController().editCalendar(id, et.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Editar calendario");

        return builder.create();
    }
}
