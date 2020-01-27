package com.photogallery.io;

import com.photogallery.model.ApiError;
import com.photogallery.model.ImageDataResponse;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class FlickrService {

    public static final String API_KEY = "a7772d57aaf9691f4029231980624a4a";
    public static final String API_METHOD = "flickr.photos.search";
    public static final String FORMAT = "json";
    public static final String EXTRAS = "url_s";
    public static final String NO_JSONCALLBACK = "1";
    private final FlickrApi flickrApi;

    public FlickrService(FlickrApi api) {
        this.flickrApi = api;
    }

    public Subscription getImagesList(final GetImagesListCallBack callback, String searchText) {

        return flickrApi.getImagesList(API_KEY, API_METHOD, searchText, EXTRAS, FORMAT, NO_JSONCALLBACK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends ImageDataResponse>>() {
                    @Override
                    public Observable<? extends ImageDataResponse> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                }).subscribe(new Subscriber<ImageDataResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new ApiError(e));
                    }

                    @Override
                    public void onNext(ImageDataResponse imageDataResponse) {
                        callback.onSuccess(imageDataResponse);
                    }
                });

    }


    public interface GetImagesListCallBack {

        void onSuccess(ImageDataResponse imageDataResponse);

        void onError(ApiError error);
    }

}
