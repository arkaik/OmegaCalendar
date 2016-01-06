package io.falc.omegacalendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class Fragment2 extends Fragment {
    RecyclerView recview;
    RecyclerView.Adapter rvadapter;
    RecyclerView.LayoutManager rvmanager;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recview = (RecyclerView) getActivity().findViewById(R.id.RecView);

        rvmanager = new LinearLayoutManager(getActivity());
        recview.setLayoutManager(rvmanager);

        MainActivity ma = (MainActivity) getActivity();

        String[] cjt = ma.getCalendarController().getCalendarsData();

        if (cjt != null && cjt.length > 0) {

            rvadapter = new RVCalendarAdapter(cjt, getActivity());
            recview.setAdapter(rvadapter);

        } else {
            cjt = new String[1];
            cjt[0] = "Oh, parece que no hay ningún calendario. (;_;)\n" +
                    "Puedes crear un Calendario Local, si quieres. (^_^)";

            rvadapter = new RVCalendarAdapter(cjt, getActivity());
            recview.setAdapter(rvadapter);

        }

    }

}
