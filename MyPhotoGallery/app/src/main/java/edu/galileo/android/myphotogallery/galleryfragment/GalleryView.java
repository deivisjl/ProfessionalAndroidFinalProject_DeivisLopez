package edu.galileo.android.myphotogallery.galleryfragment;

import java.util.List;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public interface GalleryView {
    void showRecycler();
    void hideRecycler();
    void showProgress();
    void hideProgress();

    void setImages(List<ImageGallery> imageList);
    void onEmpty(String message);
    void onDeleteImage(ImageGallery image);
}
