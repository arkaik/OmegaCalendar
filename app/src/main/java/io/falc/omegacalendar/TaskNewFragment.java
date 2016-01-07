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
 * Created by falc on 6/01/16.
 */
public class TaskNewFragment extends DialogFragment {

    private CalendarController cc;
    private long evID;
    private EditText et;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vi = inflater.inflate(R.layout.tasknew_dialog, null);

        cc = ((EventEditActivity) getActivity()).getCalendarController();
        Bundle b = getArguments();
        evID = b.getLong("evID");

        et = (EditText) vi.findViewById(R.id.calName);

        builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cc.addTask(evID,et.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setTitle("Nuevo calendario");

        return builder.create();
    }
}
