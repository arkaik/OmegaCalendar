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
public class TaskName2Fragment extends DialogFragment {

    private EditText et;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final MainActivity a = (MainActivity) getActivity();

        final Bundle bun = getArguments();

        View vi = inflater.inflate(R.layout.taskname2_dialog, null);

        et = (EditText) vi.findViewById(R.id.etTaskName);

        builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a.getCalendarController().addTask(bun.getLong("evid"),et.getText().toString());
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
