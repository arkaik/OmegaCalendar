package io.falc.omegacalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by falc on 5/01/16.
 */
public class CalendarDeleteFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle arg = getArguments();

        final MainActivity a = (MainActivity) getActivity();



        builder
                .setMessage("¿Seguro que quieres borrar el calendario?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Long id = arg.getLong("calendar");
                        a.getCalendarController().deleteCalendar(id);

                        a.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, new Fragment2())
                                .commit();
                    }
                });

        return builder.create();
    }
}
