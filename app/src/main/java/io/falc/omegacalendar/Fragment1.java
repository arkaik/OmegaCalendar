package io.falc.omegacalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Fragment1 extends Fragment {

    private int[] day = {R.id.sundayRelativeLayout, R.id.mondayRelativeLayout, R.id.tuesdayRelativeLayout, R.id.wednesdayRelativeLayout,
    R.id.thursdayRelativeLayout, R.id.fridayRelativeLayout, R.id.saturdayRelativeLayout};
    private int[] dayHeader = {R.id.sundayHeaderRelativeLayout, R.id.mondayHeaderRelativeLayout, R.id.tuesdayHeaderRelativeLayout, R.id.wednesdayHeaderRelativeLayout,
    R.id.thursdayHeaderRelativeLayout, R.id.fridayHeaderRelativeLayout, R.id.saturdayHeaderRelativeLayout};

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setFirstDayOfWeek(Calendar.MONDAY);

        final float scale = getContext().getResources().getDisplayMetrics().density;

        int curDay = gc.get(Calendar.DAY_OF_WEEK)-1;
        int curMonth = gc.get(Calendar.MONTH);
        int curHour = gc.get(Calendar.HOUR_OF_DAY);
        int curMinute = gc.get(Calendar.MINUTE);
        Log.i("fragment1", gc.getTime().toString());

        TextView cy = (TextView) getActivity().findViewById(R.id.currentYearTextView);
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyy", Locale.getDefault());
        String yeah = sdfy.format(gc.getTime());
        cy.setText(yeah);

        RelativeLayout mhead = (RelativeLayout) getActivity().findViewById(R.id.mondayHeaderRelativeLayout);
        mhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DayViewFragment dvf = new DayViewFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, dvf).commit();

            }
        });

        int vh = curHour*60+curMinute;
        View curdayweek = new View(getContext());
        curdayweek.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_accent));
        RelativeLayout.LayoutParams cdw = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int) (2*scale+0.5f));
        cdw.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        RelativeLayout dayh = (RelativeLayout) getActivity().findViewById(dayHeader[curDay]);
        dayh.addView(curdayweek, cdw);

        LinearLayout timeMark = (LinearLayout) getActivity().findViewById(R.id.currentTimeMarkerLinearLayout);
        int curtime = (int) (vh * scale + 0.5f);
        RelativeLayout.LayoutParams tm = (RelativeLayout.LayoutParams) timeMark.getLayoutParams();
        tm.setMargins(0,curtime,0,0);

        gc.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        gc.set(Calendar.MINUTE, 0);
        long stweek = gc.getTimeInMillis();
        Log.i("fragment4", gc.getTime().toString());

        gc.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        gc.set(Calendar.HOUR_OF_DAY, 12);
        gc.set(Calendar.MINUTE, 59);
        long enweek = gc.getTimeInMillis();
        Log.i("fragment4", gc.getTime().toString());

        String[] data = ((MainActivity) getActivity()).getCalendarController().getInstancesInRange(stweek,enweek);

        if (data != null && data.length > 0) {
            for (String s : data)
            {
                String[] args = s.split("[|]");
                if (args.length > 1) {
                    long st = Long.parseLong(args[1]);
                    gc.setTimeInMillis(st);

                    int h1 = gc.get(Calendar.HOUR_OF_DAY);
                    int m1 = gc.get(Calendar.MINUTE);
                    int v1 = h1*60+m1;
                    Log.i("Instances", h1 + ":" + m1 + " (" + v1 + ")");

                    int day_week = gc.get(Calendar.DAY_OF_WEEK)-1;
                    Log.i("Instances", "Dia " + day_week);

                    long en = Long.parseLong(args[2]);
                    gc.setTimeInMillis(en);

                    int h2 = gc.get(Calendar.HOUR_OF_DAY);
                    int m2 = gc.get(Calendar.MINUTE);
                    int v2 = h2*60+m2;
                    Log.i("Instances", h2+":"+m2+" ("+v2+")");

                    int delta = v2-v1;
                    Log.i("Instances_Delta", delta+" "+Integer.toHexString(day[day_week]));

                    int pixels1 = (int) (delta * scale + 0.5f);
                    int pixels2 = (int) (v1 * scale + 0.5f);

                    RelativeLayout rl1 = (RelativeLayout) getActivity().findViewById(day[day_week]);
                    Log.i("Instances_Delta", delta+" "+Integer.toHexString(day[day_week]));
                    View v = new View(getContext());
                    v.setBackgroundColor(0xffff0000);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pixels1);
                    lp.setMargins(0, pixels2, 0, 0);
                    rl1.addView(v, lp);
                }
            }
        }


    }


}
