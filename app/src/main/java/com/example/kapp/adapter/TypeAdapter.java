package com.example.kapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapp.R;
import com.example.kapp.model.Category;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.ViewHolder>{
    private List<Category> list;
    private int active = 0;
    private Context context;
    private TypeListener listener;
    public TypeAdapter(){
        list = new ArrayList<>();
    }

    public void setListener(TypeListener listener) {
        this.listener = listener;
    }

    public void setList(List<Category> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCategoryActive(){
        try{
            return list.get(active).getId();
        }catch(Exception e){
            return -1;
        }
    }

    public void setActive(int active) {
        this.active = active;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.type_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category c = list.get(position);
        holder.ll.setVisibility(View.INVISIBLE);
        holder.tv.setTextColor(holder.itemView.getResources().getColor(R.color.gray));
        holder.tv.setText(c.getName());
        if(position == active){
            holder.ll.setVisibility(View.VISIBLE);
            holder.tv.setTextColor(holder.itemView.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv;
        private LinearLayout ll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.type_item_name);
            ll = itemView.findViewById(R.id.type_item_underline);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           listener.changeActive(getAdapterPosition());
        }
    }

    public interface TypeListener{
        public void changeActive(int active);
    }
}
