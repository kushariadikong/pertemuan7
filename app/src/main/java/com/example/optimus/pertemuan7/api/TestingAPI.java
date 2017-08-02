package com.example.optimus.pertemuan7.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by OPTIMUS on 26/07/2017.
 */

public class TestingAPI {
    private static String baseURL = "https://api.github.com/";
    private static Retrofit retrofit = null;

    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseURL)
                    .build();
        }
        return retrofit;
    }
}
