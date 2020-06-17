package com.awon.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.awon.newsapp.database.Entry;
import com.awon.newsapp.util.PassModel;
import com.awon.newsapp.R;
import com.awon.newsapp.model.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;

import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private List<Entry> newsList;
    private Context context;
    private PassModel passModel;

    public void setnews(List<Entry> news, Context context, PassModel passModel) {
        this.context = context;
        this.newsList = news;
        this.passModel = passModel;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.employeeId.setText(newsList.get(position).getEmployeeId());
        holder.title.setText(newsList.get(position).getTitle());
        holder.desc.setText(newsList.get(position).getDescription());

        String URL = newsList.get(position).getUrlToImage();
        Glide.with(context)
                .load(URL)
                .asBitmap()
                .placeholder(R.drawable.placeholder_news)
                .error(R.drawable.placeholder_news)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        if (newsList != null)
            return newsList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final TextView desc;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            desc = (TextView) itemView.findViewById(R.id.desc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    passModel.passData(newsList.get(getAdapterPosition()));
                }
            });
        }
    }
}
