package com.example.instarescue;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface AccidentApi {
    @GET("accident")
    Call<AccidentResponse> checkAccident();
}

