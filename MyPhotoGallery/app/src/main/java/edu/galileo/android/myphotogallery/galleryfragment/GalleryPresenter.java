package edu.galileo.android.myphotogallery.galleryfragment;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public interface GalleryPresenter {
    void onCreate();
    void onDestroy();
    void getPhotos();
    void deletePhoto(ImageGallery image);

    void onEventMainThread(GalleryEvent event);
}
