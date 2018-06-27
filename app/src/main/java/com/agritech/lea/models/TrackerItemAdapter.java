package com.agritech.lea.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agritech.lea.R;

import java.util.List;

public class TrackerItemAdapter extends RecyclerView.Adapter<TrackerItemAdapter.MyViewHolder>{

    private List<TrackerItem> tracker;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView stage, days, date, description;

        public MyViewHolder(View view) {
            super(view);

            stage = (TextView) view.findViewById(R.id.stage);
            days = (TextView) view.findViewById(R.id.days);
            date = (TextView) view.findViewById(R.id.date);
            description = (TextView) view.findViewById(R.id.description);
        }
    }


    public TrackerItemAdapter(List<TrackerItem> tracker) {
        this.tracker = tracker;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracker_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TrackerItem item = tracker.get(position);
        holder.stage.setText(item.getStage());
        holder.days.setText(item.getDays());
        holder.date.setText(item.getDate());
        holder.description.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return tracker.size();
    }
}
