package edu.galileo.android.myphotogallery.api.entities;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class Basic<T> {
   private T photos;

    public T getPhotos() {
        return photos;
    }

    public void setPhotos(T photos) {
        this.photos = photos;
    }
}
