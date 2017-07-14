package edu.galileo.android.myphotogallery.imagesfragment;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 20/06/2017.
 */
public interface ImagesView {
    void showImage();
    void hideImage();
    void showProgress();
    void hideProgress();

    void onSaveImage(String message);
    void onEndList(String message);
    void setImageItem(ImageGallery image);
}
