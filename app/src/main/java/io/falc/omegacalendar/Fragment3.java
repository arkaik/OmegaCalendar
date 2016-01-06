package io.falc.omegacalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Fragment3 extends Fragment {
    RecyclerView recview;
    RecyclerView.Adapter rvadapter;
    RecyclerView.LayoutManager rvmanager;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recview = (RecyclerView) getActivity().findViewById(R.id.RecView);

        rvmanager = new LinearLayoutManager(getActivity());
        recview.setLayoutManager(rvmanager);

        MainActivity ma = (MainActivity) getActivity();

        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance(TimeZone.getDefault());

        Log.i("cal", Integer.toString(gc.get(GregorianCalendar.DAY_OF_WEEK)));
        gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long startMillis = gc.getTimeInMillis();

        gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        long endMillis = gc.getTimeInMillis();

        String[] cjt = ma.getCalendarController().getEventsData();

        if (cjt != null && cjt.length > 0) {

            rvadapter = new RVEventAdapter(cjt, getActivity());
            recview.setAdapter(rvadapter);

        } else {
            cjt = new String[1];
            cjt[0] = "Oh, parece que no hay ningún evento. (;_;)\n" +
                    "Puedes crear un Evento, si quieres. (^_^)";

            rvadapter = new RVEventAdapter(cjt, getActivity());
            recview.setAdapter(rvadapter);

        }

    }
}
