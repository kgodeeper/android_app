package com.example.kapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapp.R;
import com.squareup.picasso.Picasso;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.ViewHolder> {
    private String[] list;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        list = new String[]{
            "https://cdn4.vectorstock.com/i/1000x1000/02/33/sherlock-holmes-banners-detective-vector-17980233.jpg", "https://phimnhua.com/wp-content/uploads/2021/07/The-Call-Of-The-Wild.jpg", "http://redsvn.net/wp-content/uploads/2019/04/Water-margin.jpg"
        };
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String uri = list[position];
        Picasso.with(context)
                .load(uri)
                .placeholder(R.drawable.ic_baseline_book_24)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.slide_item_img);
        }
    }
}
