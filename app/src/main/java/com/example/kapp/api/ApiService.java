package com.example.kapp.api;

import com.example.kapp.config.Host;
import com.example.kapp.model.AvgVote;
import com.example.kapp.model.Chapter;
import com.example.kapp.model.ChapterList;
import com.example.kapp.model.ChapterNumber;
import com.example.kapp.model.Creation;
import com.example.kapp.model.CreationLike;
import com.example.kapp.model.Like;
import com.example.kapp.model.ListCategory;
import com.example.kapp.model.ListCreation;
import com.example.kapp.model.Token;
import com.example.kapp.model.User;
import com.example.kapp.model.Vote;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    Gson gson = new GsonBuilder().
            setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService =
            new Retrofit.Builder()
            .baseUrl(Host.address)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/get-all-creation")
    Call<ListCreation> get_all_creation();

    @GET("/get-top-update")
    Call<ListCreation> get_top_update();

    @GET("/get-creation-category/{id}")
    Call<ListCategory> get_creation_category(@Path("id") int id);

    @GET("/get-all-category")
    Call<ListCategory> get_all_category();

    @GET("/get-creation-by-category/{id}")
    Call<ListCreation> get_creation_by_cate(@Path("id") int id);

    @GET("/get-creation/{id}")
    Call<Creation> get_creation(@Path("id") int id);

    @GET("/get-chapter-number/{id}")
    Call<ChapterNumber> get_chapter_number(@Path("id") int id);

    @GET("/get-creation-like/{id}")
    Call<Like> get_creation_like(@Path("id") int id);

    @GET("/get-vote-info/{id}")
    Call<AvgVote> get_vote_avg(@Path("id") int id);

    @GET("/get-chapter-list/{id}")
    Call<ChapterList> get_chapter_list(@Path("id") int id);

    @GET("/get-chapter/{create_id}/{chapter}")
    Call<Chapter> get_chapter(@Path("create_id") int id,@Path("chapter") int chapter);

    @GET("/get-creation-rank")
    Call<ListCreation> get_creation_rank();

    @GET("/creation-filter/{keyword}")
    Call<ListCreation> creation_filter(@Path("keyword") String keyword);

    @POST("/user-login")
    Call<User> user_login(@Body User user);

    @POST("/user-register")
    Call<User> user_register(@Body User user);

    @GET("/user-like/{creation}")
    Call<CreationLike> user_like(@HeaderMap Map<String,String> headers,@Path("creation") int creation);

    @POST("/like-request")
    Call<CreationLike> user_like(@HeaderMap Map<String,String> headers, @Body CreationLike like);

    @GET("/get-vote/{id}")
    Call<List<Vote>> get_vote(@Path("id") int id);

    @POST("/user-vote")
    Call<Vote> user_vote(@HeaderMap Map<String,String> map,@Body Vote vote);
}
