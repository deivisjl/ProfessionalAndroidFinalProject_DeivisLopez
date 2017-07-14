package edu.galileo.android.myphotogallery.mainscreenfragment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.lib.ImageLoader;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public class ImagesGridAdapter extends RecyclerView.Adapter<ImagesGridAdapter.ViewHolder> {

    List<MyImage> myImageList;
    OnImagesClickListener clickListener;
    ImageLoader loader;


    public ImagesGridAdapter(List<MyImage> myImageList, OnImagesClickListener clickListener, ImageLoader loader) {
        this.myImageList = myImageList;
        this.clickListener = clickListener;
        this.loader = loader;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.images_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyImage image = myImageList.get(position);

        loader.load(holder.gridImageView,image.getImageUrl());
        holder.setOnImageClickListener(image,clickListener);

    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }

    public void addImage(MyImage image){
        myImageList.add(0,image);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.gridImageView)
        ImageView gridImageView;

        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);
        }

        public void setOnImageClickListener(final MyImage image, final OnImagesClickListener listener){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click(image);
                }
            });
        }
    }
}
