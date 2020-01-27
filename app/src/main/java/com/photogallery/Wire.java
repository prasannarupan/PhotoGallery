package com.photogallery;

import com.photogallery.io.ApiCaller;
import com.photogallery.view.ImagesListActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiCaller.class,})
public interface Wire {
    void inject(ImagesListActivity imagesListActivity);
}
