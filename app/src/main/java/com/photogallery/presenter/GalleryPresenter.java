package com.photogallery.presenter;

import com.photogallery.io.FlickrService;
import com.photogallery.model.ApiError;
import com.photogallery.model.ImageDataResponse;
import com.photogallery.view.GalleryView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class GalleryPresenter {


    private final GalleryView galleryView;
    private final FlickrService flickrService;
    private CompositeSubscription compositeSubscription;

    public GalleryPresenter(FlickrService service, GalleryView view) {
        this.galleryView = view;
        this.compositeSubscription = new CompositeSubscription();
        this.flickrService = service;
    }

    public void getImagesList(String searchKey) {
        galleryView.showWait();

        Subscription subscription = flickrService.getImagesList(new FlickrService.GetImagesListCallBack() {
            @Override
            public void onSuccess(ImageDataResponse imageDataResponse) {
                galleryView.removeWait();
                galleryView.getImageListSuccess(imageDataResponse);
            }

            @Override
            public void onError(ApiError networkError) {
                galleryView.removeWait();
                galleryView.onFailure(networkError.getAppErrorMessage());
            }

        }, searchKey);

        compositeSubscription.add(subscription);

    }

    public void onStop() {
        compositeSubscription.unsubscribe();
    }
}
