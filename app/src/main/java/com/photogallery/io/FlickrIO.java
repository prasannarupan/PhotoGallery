package com.photogallery.io;

import com.photogallery.model.ImageData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface FlickrIO {

    @GET("/rest/?{api_key}@method=flickr.photos.search&text={search_keyword}")
    Observable<ImageData> getImages(@Path(value="api_key")String apiKey, @Path(value="search_keyword")String searchkeyword);

}
