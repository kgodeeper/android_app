package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.adapter.ChapterListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.config.Host;
import com.example.kapp.model.AvgVote;
import com.example.kapp.model.ChapterList;
import com.example.kapp.model.Creation;
import com.example.kapp.model.ChapterNumber;
import com.example.kapp.model.CreationLike;
import com.example.kapp.model.Like;
import com.example.kapp.model.Token;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationActivity extends AppCompatActivity {
    private int id,count;
    private ImageView img;
    private RecyclerView rcv;
    private ChapterListAdapter cadt;
    private LinearLayout under;
    private boolean all = false;
    private TextView name,author,chapter,cate,vote,like,comment,desc,back,see_all,read,btn_like;
    private Map<String,String> headers;
    private TextView raterd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
        init();
    }

    private void setupView(Creation c, ChapterNumber cn, AvgVote av, Like l){
        Picasso.with(getApplicationContext())
                .load(Host.address + c.getImage())
                .placeholder(R.drawable.ic_baseline_book_24)
                .into(img);
        name.setText(c.getName());
        author.setText("Tác giả: " + c.getAuthor().getFullname());
        desc.setText("GIỚI THIỆU:\n"+ c.getDesc());
        chapter.setText("Số chương: " + cn.getNumber());
        String catestr = "";
        if(c.getCategory().size() > 0 ){
            catestr += " " + c.getCategory().get(0).getName();
        }
        for(int i = 1; i < c.getCategory().size(); i++){
            catestr += ", " + c.getCategory().get(i).getName();
        }
        cate.setText("Thể loại: " + catestr);
        vote.setText("Điểm đánh giá: " + (av.getAvg() != 0 ? av.getAvg() : "Chưa có"));
        like.setText(String.valueOf(l.getLike()));
        comment.setText(String.valueOf(av.getNum()));
    }

    private void callApi(){
        ApiService.apiService.get_creation(id)
                .enqueue(new Callback<Creation>() {
                    @Override
                    public void onResponse(Call<Creation> call, Response<Creation> response) {
                        Creation c = response.body();
                        if(c != null){
                            ApiService.apiService.get_chapter_number(id)
                                    .enqueue(new Callback<ChapterNumber>() {
                                        @Override
                                        public void onResponse(Call<ChapterNumber> call, Response<ChapterNumber> response) {
                                            ChapterNumber cn = response.body();
                                            setCount(cn.getNumber());
                                            if(cn!=null){
                                                ApiService.apiService.get_creation_like(id)
                                                        .enqueue(new Callback<Like>() {
                                                            @Override
                                                            public void onResponse(Call<Like> call, Response<Like> response) {
                                                                Like l = response.body();
                                                                if(l!=null){
                                                                    ApiService.apiService.get_vote_avg(id)
                                                                            .enqueue(new Callback<AvgVote>() {
                                                                                @Override
                                                                                public void onResponse(Call<AvgVote> call, Response<AvgVote> response) {
                                                                                    AvgVote avg = response.body();
                                                                                    if(avg != null){
                                                                                        cadt.setId(id);
                                                                                        cadt.setCount(cn.getNumber());
                                                                                        setupView(c,cn,avg,l);
                                                                                    }
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<AvgVote> call, Throwable t) {
                                                                                    Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, Bạn sẽ chuyển sang offline",Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<Like> call, Throwable t) {
                                                                Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, Bạn sẽ chuyển sang offline",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ChapterNumber> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, Bạn sẽ chuyển sang offline",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onFailure(Call<Creation> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, Bạn sẽ chuyển sang offline",Toast.LENGTH_SHORT).show();
                    }
                });
        ApiService.apiService.get_chapter_list(id)
                .enqueue(new Callback<ChapterList>() {
                    @Override
                    public void onResponse(Call<ChapterList> call, Response<ChapterList> response) {
                        ChapterList list = response.body();
                        cadt.setList(list.getList());
                    }

                    @Override
                    public void onFailure(Call<ChapterList> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Không thể kết nối đến máy chủ, Bạn sẽ chuyển sang offline",Toast.LENGTH_SHORT).show();
                    }
                });

        ApiService.apiService.user_like(headers,id)
                .enqueue(new Callback<CreationLike>() {
                    @Override
                    public void onResponse(Call<CreationLike> call, Response<CreationLike> response) {
                        CreationLike l = response.body();
                        if(!l.getUsername().equals("")){
                            btn_like.setText("HỦY THÍCH");
                            btn_like.setTextColor(getResources().getColor(R.color.primary));
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_solid,0,0,0);
                        }else{
                            btn_like.setText("THÍCH");
                            btn_like.setTextColor(getResources().getColor(R.color.gray));
                            like.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_regular,0,0,0);
                        }
                    }

                    @Override
                    public void onFailure(Call<CreationLike> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setCount(int count){
        this.count = count;
    }

    private void init(){
        img = findViewById(R.id.create_img);
        name = findViewById(R.id.create_name);
        chapter = findViewById(R.id.create_chapter);
        cate = findViewById(R.id.create_cate);
        vote = findViewById(R.id.create_point);
        like = findViewById(R.id.create_like);
        comment = findViewById(R.id.create_comment);
        author = findViewById(R.id.create_author);
        desc = findViewById(R.id.create_desc);
        back = findViewById(R.id.create_back);
        rcv = findViewById(R.id.create_chapter_list);
        under = findViewById(R.id.under_line);
        see_all = findViewById(R.id.create_view_all_tv);
        read = findViewById(R.id.create_read);
        btn_like = findViewById(R.id.create_btn_like);
        raterd = findViewById(R.id.btn_rate_redirect);

        headers = new HashMap<>();
        headers.put("Authorization","Bear " + new Token().getToken());

        under.setVisibility(View.INVISIBLE);

        cadt = new ChapterListAdapter();
        rcv.setAdapter(cadt);
        rcv.setLayoutManager(new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false));
        id = -1;
        try{
            Intent intent = getIntent();
            id = intent.getIntExtra("id",-1);
            callApi();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),"Lỗi",Toast.LENGTH_SHORT).show();
            //finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        see_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                all = !all;
                if(all){
                    see_all.setText("Thu lại");
                    cadt.setSlist(cadt.getList());
                    under.setVisibility(View.VISIBLE);
                }else{
                    see_all.setText("Xem tất cả");
                    cadt.setSlist(new ArrayList<>());
                    under.setVisibility(View.INVISIBLE);
                }
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),ReadActivity.class);
                if(id != -1) {
                    intent.putExtra("id", id);
                    intent.putExtra("chapter",1);
                    intent.putExtra("count",count);
                    startActivity(intent);
                }
            }
        });

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.user_like(headers,new CreationLike(new Token().getUsername(),id))
                        .enqueue(new Callback<CreationLike>() {
                            @Override
                            public void onResponse(Call<CreationLike> call, Response<CreationLike> response) {
                                callApi();
                            }

                            @Override
                            public void onFailure(Call<CreationLike> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        raterd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),VoteActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }
}