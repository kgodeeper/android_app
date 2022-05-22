package com.example.kapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapp.CreationActivity;
import com.example.kapp.R;
import com.example.kapp.config.Host;
import com.example.kapp.model.Creation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HListAdapter extends RecyclerView.Adapter<HListAdapter.ViewHolder>{
    private List<Creation> list;
    private Context context;

    public HListAdapter(){
        list = new ArrayList<>();
    }

    public void setList(List<Creation> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.h_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Creation c = list.get(position);
        Picasso.with(context)
                .load(Host.address + c.getImage())
                .placeholder(R.drawable.ic_baseline_book_24)
                .into(holder.img);
        holder.tv.setText(c.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.h_item_img);
            tv = itemView.findViewById(R.id.h_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = list.get(getAdapterPosition()).getId();
            Intent intent = new Intent(view.getContext(), CreationActivity.class);
            intent.putExtra("id",id);
            view.getContext().startActivity(intent);
        }
    }
}
