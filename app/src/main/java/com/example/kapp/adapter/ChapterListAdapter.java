package com.example.kapp.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapp.R;
import com.example.kapp.ReadActivity;
import com.example.kapp.model.Chapter;

import java.util.ArrayList;
import java.util.List;

public class ChapterListAdapter extends RecyclerView.Adapter<ChapterListAdapter.ViewHolder> {
    private List<Chapter> list,slist;
    private int id;
    private int count;

    public ChapterListAdapter(){
        list = new ArrayList<>();
        slist = list;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCount(int count){this.count = count;}

    public void setList(List<Chapter> list){
        this.list = list;
    }

    public List<Chapter> getList(){
        return this.list;
    }

    public void setSlist(List<Chapter> list){
        this.slist = list;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chapter c = slist.get(position);
        holder.tv.setText("Chương " + c.getChapter() + ": "  + c.getName());
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.chapter_item_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReadActivity.class);
            intent.putExtra("id",id);
            intent.putExtra("chapter",list.get(getAdapterPosition()).getChapter());
            intent.putExtra("count",count);
            view.getContext().startActivity(intent);
        }
    }
}
