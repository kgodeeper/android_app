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
import com.example.kapp.model.Category;
import com.example.kapp.model.Creation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VListAdapter extends RecyclerView.Adapter<VListAdapter.ViewHolder> {
    private List<Creation> list;
    private Context context;
    private int height;
    public VListAdapter(){
        list = new ArrayList<>();
    }

    public void setList(List<Creation> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getHeight(){
        return height;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = context;
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.v_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Creation c = list.get(position);
        Picasso.with(context)
                .load(Host.address + c.getImage())
                .placeholder(R.drawable.ic_baseline_book_24)
                .into(holder.img);
        holder.name.setText(c.getName());
        holder.author.setText("Tác giả: " + c.getAuthor().getFullname());
        String catestr = "";
        if(c.getCategory().size() > 0 ){
            catestr += " " + c.getCategory().get(0).getName();
        }
        for(int i = 1; i < c.getCategory().size(); i++){
            catestr += ", " + c.getCategory().get(i).getName();
        }
        holder.cate.setText("Thể loại: " + catestr);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView name,author,cate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.v_item_img);
            name = itemView.findViewById(R.id.v_item_name);
            author = itemView.findViewById(R.id.v_item_author);
            cate = itemView.findViewById(R.id.v_item_cate);
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
