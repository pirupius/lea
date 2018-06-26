package com.agritech.lea.models;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agritech.lea.R;

import java.util.List;

public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.MyViewHolder>{

    private List<NewsItem> news;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, brief, mdate, id, date;

        public MyViewHolder(View view) {
            super(view);

            id = (TextView) view.findViewById(R.id.id);
            title = (TextView) view.findViewById(R.id.title);
            brief = (TextView) view.findViewById(R.id.brief);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    public NewsItemAdapter(List<NewsItem> news) {
        this.news = news;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NewsItem item = news.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.brief.setText(item.getBrief());
        holder.id.setText(item.getId());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
