package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.adapter.RankListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.model.Creation;
import com.example.kapp.model.ListCategory;
import com.example.kapp.model.ListCreation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RankActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private RankListAdapter radt;
    private TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        init();
    }

    private void init(){
        rcv = findViewById(R.id.rank_list);
        back = findViewById(R.id.rank_btn_back);
        radt = new RankListAdapter();
        rcv.setAdapter(radt);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        ApiService.apiService.get_creation_rank()
                .enqueue(new Callback<ListCreation>() {
                    @Override
                    public void onResponse(Call<ListCreation> call, Response<ListCreation> response) {
                        ListCreation list = response.body();
                        ArrayList<Creation> newlist = new ArrayList<>();
                        for(Creation i:list.getCreations()){
                            ApiService.apiService.get_creation_category(i.getId())
                                    .enqueue(new Callback<ListCategory>() {
                                        @Override
                                        public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                                            i.setCategory(response.body().getCategories());
                                            newlist.add(i);
                                            radt.setList(newlist);
                                        }

                                        @Override
                                        public void onFailure(Call<ListCategory> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, bạn sẽ chuyển sang offline",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<ListCreation> call, Throwable t) {
                        System.out.println(t);
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}