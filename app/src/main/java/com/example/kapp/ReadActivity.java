package com.example.kapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kapp.api.ApiService;
import com.example.kapp.model.Chapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadActivity extends AppCompatActivity {
    private int id,chap,count;
    private TextView back,content,title,setting;
    private ScrollView scroll;
    private boolean set;
    private ConstraintLayout slayout;
    private Button apply,next;
    private Spinner font,color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        init();
    }

    private void setupView(Chapter c){
        title.setText("Chương " + c.getChapter() + ": " + c.getName());
        content.setText(c.getContent());
        if (chap == count) {
            next.setText("Kết thúc");
        }
    }

    private void setChapter(int id,int chapter){
        ApiService.apiService.get_chapter(id,chap)
                .enqueue(new Callback<Chapter>() {
                    @Override
                    public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                        Chapter chapter = response.body();
                        if(chapter != null)
                            setupView(chapter);
                        else{
                            Toast.makeText(getApplicationContext(),"Chương không tồn tại",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Chapter> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Không có chương này ! Kết thúc.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void init(){
        id = -1;
        chap = 1;
        count = 0;
        back = findViewById(R.id.read_back);
        title = findViewById(R.id.read_title);
        content = findViewById(R.id.read_content);
        setting = findViewById(R.id.read_setting);
        scroll = findViewById(R.id.read_scroll);
        apply = findViewById(R.id.set_apply);
        next = findViewById(R.id.read_next);
        color = findViewById(R.id.color_select);
        font = findViewById(R.id.font_select);
        slayout = findViewById(R.id.setting_bound);
        slayout.setVisibility(View.INVISIBLE);
        content.setTextSize(16);
        font.setSelection(1);
        try{
            Intent intent = getIntent();
            id = intent.getIntExtra("id",-1);
            chap = intent.getIntExtra("chapter",1);
            count = intent.getIntExtra("count",0);
            setChapter(id,chap);
        }catch(Exception e){
            finish();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(set){
                    slayout.setVisibility(View.INVISIBLE);
                }else{
                    slayout.setVisibility(View.VISIBLE);
                }
                set = !set;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chap < count) {
                    chap = chap + 1;
                    scroll.smoothScrollTo(0, 0);
                    setChapter(id, chap);
                }else{
                    finish();
                }
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int f = font.getSelectedItemPosition();
                int c = color.getSelectedItemPosition();
                switch(f){
                    case 0:{
                        content.setTextSize(14);
                        break;
                    }
                    case 1:{
                        content.setTextSize(16);
                        break;
                    }
                    case 2:{
                        content.setTextSize(20);
                        break;
                    }
                    default:break;
                }
                switch(c){
                    case 0:{
                        content.setTextColor(view.getResources().getColor(R.color.black));
                        break;
                    }
                    case 1:{
                        content.setTextColor(view.getResources().getColor(R.color.gray));
                        break;
                    }
                    case 2:{
                        content.setTextColor(view.getResources().getColor(R.color.primary));
                        break;
                    }
                    default:break;
                }
                slayout.setVisibility(View.INVISIBLE);
                set = false;
            }
        });
    }
}