package edu.galileo.android.myphotogallery.galleryfragment.adapter;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public interface OnImageItemClickListener {
    void onImageClick(ImageGallery image);
    void onDeleteClick(ImageGallery image);
}
