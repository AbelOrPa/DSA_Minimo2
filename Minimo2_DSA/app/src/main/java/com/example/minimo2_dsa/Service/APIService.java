package com.example.minimo2_dsa.Service;

import com.example.minimo2_dsa.Models.Repositorio;
import com.example.minimo2_dsa.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("users/{username}")
    Call<User> getUser(@Path(value = "username") String username);

    @GET("users/{username}/repos")
    Call<List<Repositorio>> getRepos(@Path(value = "username")String username);

}
