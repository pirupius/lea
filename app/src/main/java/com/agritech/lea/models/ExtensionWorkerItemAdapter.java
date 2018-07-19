package com.agritech.lea.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agritech.lea.R;

import java.util.List;

public class ExtensionWorkerItemAdapter extends RecyclerView.Adapter<ExtensionWorkerItemAdapter.MyViewHolder>{

    private List<ExtensionWorkerItem> extension_workers;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id, name, location, chemicals, phone;

        public MyViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.id);
            name = (TextView) view.findViewById(R.id.name);
            location = (TextView) view.findViewById(R.id.location);
            chemicals = (TextView) view.findViewById(R.id.chemicals);
            phone = (TextView) view.findViewById(R.id.phone);
        }
    }


    public ExtensionWorkerItemAdapter(List<ExtensionWorkerItem> extension_workers) {
        this.extension_workers = extension_workers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supplier_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ExtensionWorkerItem item = extension_workers.get(position);
        holder.name.setText(item.getName());
        holder.location.setText(item.getLocation());
        holder.chemicals.setText(item.getChemicals());
        holder.phone.setText(item.getPhone());
        holder.id.setText(item.getId());
    }

    @Override
    public int getItemCount() {
        return extension_workers.size();
    }
}
