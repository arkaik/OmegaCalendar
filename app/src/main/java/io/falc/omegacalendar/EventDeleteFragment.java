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
public class EventDeleteFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final Bundle arg = getArguments();

        final MainActivity a = (MainActivity) getActivity();

        builder
                .setMessage("¿Seguro que quieres borrar el evento?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = arg.getString("event");
                        String[] sarg = s != null ? s.split("[|]") : new String[0];
                        Long id = Long.getLong(sarg[0]);
                        a.getCalendarController().deleteEvent(id);

                        a.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_frame, new Fragment3())
                                .commit();
                    }
                });

        return builder.create();
    }
}
