package com.example.recyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyApi {
    @GET("posts")
    Call<List<ApiModel>> getModels();
}
