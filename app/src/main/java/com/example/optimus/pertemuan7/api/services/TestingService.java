package com.example.optimus.pertemuan7.api.services;

import com.example.optimus.pertemuan7.api.model.Repo;
import com.example.optimus.pertemuan7.api.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by OPTIMUS on 26/07/2017.
 */

public interface TestingService {
    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("search/repositories")
    Call<Repository> repositories(@Query("q") String query,
                                  @Query("sort") String sort,
                                  @Query("order") String order,
                                  @Query("page") Integer page,
                                  @Query("per_page") Integer per_page);
}
