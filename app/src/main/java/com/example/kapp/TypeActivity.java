package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.adapter.TypeAdapter;
import com.example.kapp.adapter.VListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.model.Creation;
import com.example.kapp.model.ListCategory;
import com.example.kapp.model.ListCreation;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeActivity extends AppCompatActivity implements TypeAdapter.TypeListener{
    private TextView backbtn;
    private RecyclerView tname,tlist;
    private TypeAdapter tadt;
    private VListAdapter vadt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        init();
    }

    private void init(){
        backbtn = findViewById(R.id.type_btn_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tname = findViewById(R.id.type_list);
        tlist = findViewById(R.id.type_list_create);
        tadt = new TypeAdapter();
        vadt = new VListAdapter();

        tadt.setListener(this);
        tname.setAdapter(tadt);
        tname.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        ApiService.apiService.get_all_category().enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                ListCategory list = response.body();
                tadt.setList(list.getCategories());
            }

            @Override
            public void onFailure(Call<ListCategory> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Không thể kết nối ! Bạn sẽ chuyển sang chế độ offline",Toast.LENGTH_LONG).show();
            }
        });

        tlist.setAdapter(vadt);
        tlist.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        setResultList(tadt.getCategoryActive());
    }

    private void setResultList(int id){
        ApiService.apiService.get_creation_by_cate(id)
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
                        Toast.makeText(getApplicationContext(),"Không thể kết nối ! Bạn sẽ chuyển sang chế độ offline",Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void changeActive(int active) {
        tadt.setActive(active);
        setResultList(tadt.getCategoryActive());
    }
}