package com.photogallery.io;


import com.photogallery.model.ImageDataResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrApi {

    //https://api.flickr.com/services/rest/?api_key=a7772d57aaf9691f4029231980624a4a&method=flickr.photos.search&text=cars&extras=url_s&format=json&nojsoncallback=1
    @GET("/services/rest/")
    Observable<ImageDataResponse> getImagesList(@Query(value = "api_key") String apiKey, @Query(value = "method") String apiMethod, @Query(value = "text") String searchKeyword, @Query(value = "extras") String extras, @Query(value = "format") String format, @Query(value = "nojsoncallback") String noJsonCallBack);


}
