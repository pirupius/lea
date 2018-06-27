package com.agritech.lea.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agritech.lea.R;

import java.util.List;

public class SupplierItemAdapter extends RecyclerView.Adapter<SupplierItemAdapter.MyViewHolder>{

    private List<SupplierItem> suppliers;

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


    public SupplierItemAdapter(List<SupplierItem> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supplier_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SupplierItem item = suppliers.get(position);
        holder.name.setText(item.getName());
        holder.location.setText(item.getLocation());
        holder.chemicals.setText(item.getChemicals());
        holder.phone.setText(item.getPhone());
        holder.id.setText(item.getId());
    }

    @Override
    public int getItemCount() {
        return suppliers.size();
    }
}
