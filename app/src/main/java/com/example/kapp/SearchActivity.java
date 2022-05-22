package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.adapter.VListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.model.Creation;
import com.example.kapp.model.ListCategory;
import com.example.kapp.model.ListCreation;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView rcv;
    private VListAdapter vadt;
    private TextView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }

    private void init(){
        searchView = findViewById(R.id.search);
        rcv = findViewById(R.id.search_list);
        back = findViewById(R.id.search_btn_back);

        vadt = new VListAdapter();
        rcv.setAdapter(vadt);
        rcv.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ApiService.apiService.creation_filter(query)
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
                                                    vadt.setList(newlist);
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

                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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