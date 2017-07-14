package edu.galileo.android.myphotogallery.galleryfragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    List<ImageGallery> imageList;
    ImageLoader loader;
    OnImageItemClickListener listener;


    public GalleryAdapter(List<ImageGallery> imageList, ImageLoader loader, OnImageItemClickListener listener) {
        this.imageList = imageList;
        this.loader = loader;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageGallery image = imageList.get(position);

        loader.load(holder.galleryImageView, image.getImageUrl());
        holder.titleTextView.setText(image.getTitle());
        holder.setOnClickListener(image, listener);

    }

    public void setImages(List<ImageGallery> image){
        this.imageList = image;
        notifyDataSetChanged();
    }

    public void deleteImage(ImageGallery image){
        imageList.remove(image);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.titleTextView)
        TextView titleTextView;
        @Bind(R.id.galleryImageView)
        ImageView galleryImageView;
        @Bind(R.id.imgDelete)
        ImageButton imgDelete;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setOnClickListener(final ImageGallery image, final OnImageItemClickListener listener) {
            galleryImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onImageClick(image);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(image);
                }
            });
        }
    }
}
