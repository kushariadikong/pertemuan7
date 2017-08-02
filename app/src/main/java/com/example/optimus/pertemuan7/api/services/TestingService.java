package com.example.optimus.pertemuan7.api.services;

import com.example.optimus.pertemuan7.api.model.Repo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by OPTIMUS on 26/07/2017.
 */

public interface TestingService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}
