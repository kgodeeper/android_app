package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.kapp.adapter.VoteListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.config.Host;
import com.example.kapp.model.Token;
import com.example.kapp.model.Vote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private VoteListAdapter vladt;
    private int id;
    private String account;
    private RatingBar urate;
    private EditText cmt;
    private TextView back;
    private Button submit;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        init();
    }

    private void callApi(){
        ApiService.apiService.get_vote(id)
                .enqueue(new Callback<List<Vote>>() {
                    @Override
                    public void onResponse(Call<List<Vote>> call, Response<List<Vote>> response) {
                        List<Vote> vote = response.body();
                        vladt.setList(vote);
                        for(Vote v:vote){
                            if(v.getAccount().equals(account)){
                                urate.setRating(v.getVote());
                                cmt.setText(v.getVote_comment());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Vote>> call, Throwable t) {
                        finish();
                    }
                });
    }

    private void init(){
        try {
            Intent intent = getIntent();
            id = intent.getIntExtra("id",-1);
            if(id == -1) finish();

            account = new Token().getUsername();
            rcv = findViewById(R.id.list_comment);
            urate = findViewById(R.id.vote_rate);
            cmt = findViewById(R.id.vote_cmt);
            back = findViewById(R.id.vote_back);
            submit = findViewById(R.id.vote_btn_submit);
            map = new HashMap<>();
            map.put("Authorization","Bear " + new Token().getUsername());

            vladt = new VoteListAdapter();
            rcv.setAdapter(vladt);
            rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int rt = Math.round(urate.getRating());
                    String cmtstr = cmt.getText().toString();
                    Vote tmp = new Vote(account,id,rt,cmtstr,"");
                    ApiService.apiService.user_vote(map,tmp)
                    .enqueue(new Callback<Vote>() {
                        @Override
                        public void onResponse(Call<Vote> call, Response<Vote> response) {
                            callApi();
                        }

                        @Override
                        public void onFailure(Call<Vote> call, Throwable t) {
                            finish();
                        }
                    });
                }
            });
        }catch(Exception e){
            finish();
        }
    }
}