package com.example.kapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kapp.MainActivity;
import com.example.kapp.R;
import com.example.kapp.RankActivity;
import com.example.kapp.SearchActivity;
import com.example.kapp.TypeActivity;
import com.example.kapp.adapter.HListAdapter;
import com.example.kapp.adapter.SliderAdapter;
import com.example.kapp.adapter.VListAdapter;
import com.example.kapp.api.ApiService;
import com.example.kapp.model.Creation;
import com.example.kapp.model.ListCategory;
import com.example.kapp.model.ListCreation;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentLib extends Fragment {
    private RecyclerView suggest,update;
    private HListAdapter hadt;
    private VListAdapter vadt;
    private ViewPager2 vp2;
    private CircleIndicator3 indicator;
    private SliderAdapter sadt;
    private TextView homecate,homerank,homesearch;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view){
        suggest = view.findViewById(R.id.home_suggest);
        update = view.findViewById(R.id.home_new_update);
        homecate = view.findViewById(R.id.home_cate);
        homerank = view.findViewById(R.id.home_rank);
        homesearch = view.findViewById(R.id.home_search);
        vp2 = view.findViewById(R.id.home_slider);
        indicator = view.findViewById(R.id.home_indicator);
        sadt = new SliderAdapter();
        hadt = new HListAdapter();
        vadt = new VListAdapter();
        vp2.setAdapter(sadt);
        indicator.setViewPager(vp2);
        suggest.setAdapter(hadt);
        suggest.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.HORIZONTAL,false));
        ApiService.apiService.get_all_creation()
                .enqueue(new Callback<ListCreation>() {
                    @Override
                    public void onResponse(Call<ListCreation> call, Response<ListCreation> response) {
                        ListCreation list = response.body();
                        hadt.setList(list.getCreations());
                    }

                    @Override
                    public void onFailure(Call<ListCreation> call, Throwable t) {
                        Toast.makeText(view.getContext(),"Không thể kết nối đến máy chủ, bạn sẽ chuyển sang offline",Toast.LENGTH_LONG).show();
                    }
                });

        update.setAdapter(vadt);
        update.setLayoutManager(new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false));
        ApiService.apiService.get_top_update()
                .enqueue(new Callback<ListCreation>() {
                    @Override
                    public void onResponse(Call<ListCreation> call, Response<ListCreation> response) {
                        ListCreation list = response.body();
                        ArrayList<Creation> newlist = new ArrayList<>();
                        if(list != null)
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
                                            Toast.makeText(view.getContext(),"Không thể kết nối đến máy chủ, bạn sẽ chuyển sang offline",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                        ViewGroup.LayoutParams params = update.getLayoutParams();
                        params.height = 450 * list.getCreations().size();
                        update.setLayoutParams(params);
                    }

                    @Override
                    public void onFailure(Call<ListCreation> call, Throwable t) {
                        Toast.makeText(view.getContext(),"Không thể kết nối đến máy chủ, bạn sẽ chuyển sang offline",Toast.LENGTH_LONG).show();
                    }
                });
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                vp2.setCurrentItem((vp2.getCurrentItem() + 1)%(sadt.getItemCount()),true);

            }
        };
        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });

        homecate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent t = new Intent(view.getContext(), TypeActivity.class);
                startActivity(t);
            }
        });

        homerank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RankActivity.class);
                startActivity(intent);
            }
        });

        homesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
