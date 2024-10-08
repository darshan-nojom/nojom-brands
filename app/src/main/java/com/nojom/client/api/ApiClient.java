package com.nojom.client.api;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nojom.client.Task24Application;
import com.nojom.client.util.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        try {
            if (retrofit == null) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.level(HttpLoggingInterceptor.Level.BODY);

                OkHttpClient client = new OkHttpClient.Builder()
//                        .addInterceptor(new ChuckerInterceptor(Task24Application.getInstance()))
                        .addInterceptor(interceptor)
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .writeTimeout(100, TimeUnit.SECONDS)
                        .build();

                retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .client(client)
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit;
    }


}
