package io.falc.omegacalendar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

/**
 * Created by falc on 5/01/16.
 */
public class TaskNameFragment extends DialogFragment {

    private EditText et;
    private Spinner sp;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final MainActivity a = (MainActivity) getActivity();

        View vi = inflater.inflate(R.layout.taskname_dialog, null);

        et = (EditText) vi.findViewById(R.id.task_text);
        sp = (Spinner) vi.findViewById(R.id.task_spinner);

        final String[] data = a.getCalendarController().getEventsData();

        String[] names = new String[data.length];
        for (int i = 0; i < data.length; ++i)
        {
            String[] arg = data[i].split("[|]");
            names[i] = arg[1];
        }

        ArrayAdapter<String> adap = new ArrayAdapter<String>(a, android.R.layout.simple_spinner_item, android.R.id.text1, names);

        sp.setAdapter(adap);

        builder.setView(vi)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pos = sp.getSelectedItemPosition();
                        String[] s = data[pos].split("[|]");
                        if (s.length > 1) {
                            a.getCalendarController().addTask(Long.parseLong(s[0]),et.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}
