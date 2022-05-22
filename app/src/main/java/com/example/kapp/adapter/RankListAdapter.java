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
import com.example.kapp.api.ApiService;
import com.example.kapp.config.Host;
import com.example.kapp.model.Category;
import com.example.kapp.model.Creation;
import com.example.kapp.model.Like;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankListAdapter extends RecyclerView.Adapter<RankListAdapter.ViewHolder> {
    private List<Creation> list;
    private Context context;
    private int height;
    public RankListAdapter(){
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Creation c = list.get(position);
        holder.rank.setText(String.valueOf(position+1));
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
        ApiService.apiService.get_creation_like(c.getId())
                .enqueue(new Callback<Like>() {
                    @Override
                    public void onResponse(Call<Like> call, Response<Like> response) {
                        Like l = response.body();
                        holder.like.setText("Yêu thích: " + l.getLike());
                    }

                    @Override
                    public void onFailure(Call<Like> call, Throwable t) {
                        holder.like.setText("Yêu thích: undefine");
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView img;
        private TextView name,author,cate,rank,like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.r_item_rank);
            img = itemView.findViewById(R.id.r_item_img);
            name = itemView.findViewById(R.id.r_item_name);
            author = itemView.findViewById(R.id.r_item_author);
            cate = itemView.findViewById(R.id.r_item_cate);
            like = itemView.findViewById(R.id.r_item_likes);
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
