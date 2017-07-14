package edu.galileo.android.myphotogallery.imagesfragment;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 20/06/2017.
 */
public interface ImagePresenter {
    void onShow();
    void onDestroy();
    void setRepo(List<FlickrItem> list);
    void nextImage();
    void saveImage(ImageGallery image);

    void onEventMainThread(ImageEvent event);
}
