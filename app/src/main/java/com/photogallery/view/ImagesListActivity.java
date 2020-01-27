package com.photogallery.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.photogallery.DaggerWire;
import com.photogallery.R;
import com.photogallery.Wire;
import com.photogallery.io.ApiCaller;
import com.photogallery.io.FlickrService;
import com.photogallery.model.ImageData;
import com.photogallery.model.ImageDataResponse;
import com.photogallery.presenter.GalleryPresenter;

import java.io.File;

import javax.inject.Inject;

public class ImagesListActivity extends AppCompatActivity implements GalleryView {

    @Inject
    public FlickrService flickrService;
    Wire wire;
    private RecyclerView list;
    private EditText searchBox;
    private ProgressBar progressBar;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File cacheFile = new File(getCacheDir(), "image_responses");
        wire = DaggerWire.builder().apiCaller(new ApiCaller(cacheFile)).build();
        wire.inject(this);

        setContentView(R.layout.activity_scrolling);
        searchBox = findViewById(R.id.search_box);
        list = findViewById(R.id.list);
        searchButton = findViewById((R.id.search_button));
        progressBar = findViewById(R.id.progress_bar);
        list.setLayoutManager(new LinearLayoutManager(this));

        GalleryPresenter galleryPresenter = new GalleryPresenter(flickrService, this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryPresenter.getImagesList(searchBox.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(String appErrorMessage) {

    }

    @Override
    public void getImageListSuccess(ImageDataResponse imageResponse) {
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), imageResponse.getImages().getImageData(),
                new GalleryAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(ImageData imageData) {

                    }
                });

        list.setAdapter(adapter);
    }
}
