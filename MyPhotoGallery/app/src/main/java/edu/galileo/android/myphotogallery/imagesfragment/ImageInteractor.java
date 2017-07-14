package edu.galileo.android.myphotogallery.imagesfragment;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 20/06/2017.
 */
public interface ImageInteractor {
    void doSetList(List<FlickrItem> list);
    void doNextImage();
    void doSaveImage(ImageGallery image);
}
