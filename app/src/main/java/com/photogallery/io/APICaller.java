package com.photogallery.io;


import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APICaller {

    private static final String BASE_URL = "https://api.flickr.com/services/";
    private static final APICaller ourInstance = new APICaller();

    public static APICaller getInstance() {
        return ourInstance;
    }


    private APICaller() {
    }


}
