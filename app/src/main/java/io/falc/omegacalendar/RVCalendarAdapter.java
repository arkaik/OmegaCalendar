package io.falc.omegacalendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by falc on 1/01/16.
 */
public class RVCalendarAdapter extends RecyclerView.Adapter<RVCalendarAdapter.ViewHolder>{
    private String[] mDataset;
    private FragmentActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        public TextView mtext;
        public LinearLayout color_ll;
        private ImageButton mButton;
        private FragmentActivity activity;
        private int calid;
        private String calname;

        public ViewHolder(View v, FragmentActivity act)
        {
            super(v);
            mtext = (TextView) v.findViewById(R.id.calItem);
            color_ll = (LinearLayout) v.findViewById(R.id.color_ll);
            activity = act;
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            mButton = (ImageButton) v.findViewById(R.id.calButton);
            mButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == mButton)
            {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_context2);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            DialogFragment dialog;
            Bundle args = new Bundle();
            args.putLong("calendar", calid);

            switch(item.getItemId())
            {
                case R.id.action_editcal:
                    dialog = new CalendarEditFragment();
                    dialog.setArguments(args);
                    dialog.show(activity.getSupportFragmentManager(),"CalendarEditFragment");
                    return true;
                case R.id.action_delcal:
                    dialog = new CalendarDeleteFragment();
                    dialog.setArguments(args);
                    dialog.show(activity.getSupportFragmentManager(),"CalendarDeleteFragment");
                    return true;
            }
            return false;
        }

        public void setData(int id, String n)
        {
            calid = id;
            calname = n;
        }
    }

    public RVCalendarAdapter(String[] data, FragmentActivity act)
    {
        mDataset = data;
        activity = act;
    }

    @Override
    public RVCalendarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.calendar_item,parent,false);
        ViewHolder vh = new ViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(RVCalendarAdapter.ViewHolder holder, int position) {
        String args = mDataset[position];
        String[] args_data = args.split("[|]");
        if (args_data.length > 1) {

            holder.mtext.setText(args_data[1]);
            Log.i("number", args_data[2]);
            int id = Integer.parseInt(args_data[0]);
            int colour = Integer.parseInt(args_data[2]);
            holder.color_ll.setBackgroundColor(colour);
            holder.setData(id, args_data[1]);
        }
        else holder.mtext.setText(args);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

}
