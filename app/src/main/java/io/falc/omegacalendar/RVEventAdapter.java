package io.falc.omegacalendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by falc on 1/01/16.
 */
public class RVEventAdapter extends RecyclerView.Adapter<RVEventAdapter.ViewHolder>{
    private String[] mDataset;
    private FragmentActivity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        public TextView mcal;
        public TextView mtext;
        public ImageButton mbutton;

        private String arguments;

        private FragmentActivity activity;
        //public LinearLayout color_ll;
        public ViewHolder(View v, FragmentActivity act)
        {
            super(v);
            activity = act;
            mtext = (TextView) v.findViewById(R.id.evItem);
            mcal = (TextView) v.findViewById(R.id.calView);
            mbutton = (ImageButton) v.findViewById(R.id.evButton);
            mbutton.setOnClickListener(this);
            //color_ll = (LinearLayout) v.findViewById(R.id.color_ll);
        }

        @Override
        public void onClick(View v) {
            if (v == mbutton)
            {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.menu_context2);
                popup.setOnMenuItemClickListener(this);
                popup.show();
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            DialogFragment dialog;
            Bundle args = new Bundle();
            args.putString("event", arguments);

            switch(item.getItemId())
            {
                case R.id.action_editcal:
                    dialog = new EventEditFragment();
                    dialog.setArguments(args);
                    dialog.show(activity.getSupportFragmentManager(),"EventEditFragment");
                    return true;
                case R.id.action_delcal:
                    dialog = new EventDeleteFragment();
                    dialog.setArguments(args);
                    dialog.show(activity.getSupportFragmentManager(),"EventDeleteFragment");
                    return true;
            }
            return false;
        }

        public void setData(String args)
        {
            arguments = args;
        }

    }

    public RVEventAdapter(String[] data, FragmentActivity act)
    {
        mDataset = data;
        activity = act;
    }

    @Override
    public RVEventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.event_item,parent,false);
        ViewHolder vh = new ViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(RVEventAdapter.ViewHolder holder, int position) {
        String args = mDataset[position];
        String[] args_data = args.split("[|]");
        if (args_data.length > 1)
        {
            holder.setData(args);
            holder.mtext.setText(args_data[1]);
            holder.mcal.setText(args_data[3]);
        }
        else
        {
            holder.mtext.setText(args);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
