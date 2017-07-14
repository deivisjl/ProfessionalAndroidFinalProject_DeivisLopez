package edu.galileo.android.myphotogallery.galleryfragment;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public interface GalleryRepository {
    void getPhotos();
    void deletePhoto(ImageGallery image);
}
