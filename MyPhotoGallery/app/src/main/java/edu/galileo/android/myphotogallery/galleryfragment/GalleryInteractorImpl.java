package edu.galileo.android.myphotogallery.galleryfragment;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public class GalleryInteractorImpl implements GalleryInteractor {
    GalleryRepository repository;

    public GalleryInteractorImpl(GalleryRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doGetPhotos() {
        repository.getPhotos();
    }

    @Override
    public void doDeletePhoto(ImageGallery image) {
        repository.deletePhoto(image);
    }
}
