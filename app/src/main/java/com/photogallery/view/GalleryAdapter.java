package com.photogallery.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.photogallery.R;
import com.photogallery.model.ImageData;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<ImageData> data;
    private Context context;

    public GalleryAdapter(Context context, List<ImageData> data, OnItemClickListener listener) {
        this.data = data;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(GalleryAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);

        Glide.with(context)
                .load(data.get(position).getImageUrl())
                .into(holder.pic);

    }

    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onClick(ImageData Item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pic;

        public ViewHolder(View picView) {
            super(picView);
            pic = picView.findViewById(R.id.image);
        }

        public void click(final ImageData imageData, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    listener.onClick(imageData);
                }

            });
        }

    }
}
