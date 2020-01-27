package com.photogallery.model;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.photogallery.io.Response;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.HttpException;

import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

public class ApiError extends Throwable {

    public static final String INTERNET_ERROR = "No Internet Connection Available";
    public static final String DEFAULT_ERROR = "Something unexpected happened, Please try again !!! ";
    private static final String ERROR_MESSAGE_HEADER = "Error-Message";
    private final Throwable error;

    public ApiError(Throwable e) {
        super(e);
        this.error = e;
    }

    public boolean isAuthFailure() {
        return error instanceof HttpException && (((HttpException) error).code() == HTTP_UNAUTHORIZED);
    }

    public boolean isResponseNull() {
        return error instanceof retrofit2.adapter.rxjava.HttpException
                && ((retrofit2.adapter.rxjava.HttpException) error).response() == null;
    }

    public String getAppErrorMessage() {
        if (this.error instanceof IOException) return INTERNET_ERROR;
        if (!(this.error instanceof HttpException)) return DEFAULT_ERROR;
        retrofit2.Response<?> response = ((HttpException) this.error).response();
        if (response != null) {
            String status = getJsonStringFromResponse(response);
            if (!TextUtils.isEmpty(status)) return status;

            Map<String, List<String>> headers = response.headers().toMultimap();
            if (headers.containsKey(ERROR_MESSAGE_HEADER))
                return headers.get(ERROR_MESSAGE_HEADER).get(0);
        }

        return DEFAULT_ERROR;
    }

    protected String getJsonStringFromResponse(final retrofit2.Response<?> response) {
        try {
            String jsonString = response.errorBody().string();
            Response errorResponse = new Gson().fromJson(jsonString, com.photogallery.io.Response.class);
            return errorResponse.status;
        } catch (Exception e) {
            return null;
        }
    }

    public String getMessage() {
        return error.getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiError that = (ApiError) o;

        return error != null ? error.equals(that.error) : that.error == null;

    }

    @Override
    public int hashCode() {
        return error != null ? error.hashCode() : 0;
    }
}
