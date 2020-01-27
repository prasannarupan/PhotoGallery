package com.photogallery.view;

import com.photogallery.model.ImageDataResponse;

public interface GalleryView {

    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getImageListSuccess(ImageDataResponse imageResponse);
}
