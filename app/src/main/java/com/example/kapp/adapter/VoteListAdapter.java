package com.example.kapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kapp.R;
import com.example.kapp.model.Token;
import com.example.kapp.model.Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.ViewHolder>{
    private Context context;
    private List<Vote> list;

    public VoteListAdapter(){
        list = new ArrayList<>();
    }

    public void setList(List<Vote> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vote_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vote v = list.get(position);
        String updatestr = v.getUpdade_at().split("T")[0] + " " + v.getUpdade_at().split("T")[1];
        updatestr = updatestr.split(".")[0];
        if(!v.getAccount().equals(new Token().getUsername())) {
            holder.account.setText("Đánh giá bởi: " + v.getAccount() + "\n" + "Lúc: " + updatestr);
        }else{
            holder.account.setText("Đánh giá bởi: Bạn\n" + "Lúc: " + v.getUpdade_at());
        }
        holder.comment.setText(v.getVote_comment());
        holder.rate.setText("Đã đánh giá: " + v.getVote() + " sao");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView account,comment,rate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            account = itemView.findViewById(R.id.vote_account);
            comment = itemView.findViewById(R.id.vote_coment);
            rate = itemView.findViewById(R.id.vote_rate);
        }
    }
}
