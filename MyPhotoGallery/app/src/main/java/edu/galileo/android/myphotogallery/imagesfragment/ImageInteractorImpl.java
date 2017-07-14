package edu.galileo.android.myphotogallery.imagesfragment;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 21/06/2017.
 */
public class ImageInteractorImpl implements ImageInteractor {

    ImageRepository repository;

    public ImageInteractorImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doSetList(List<FlickrItem> list) {
        repository.setListRepo(list);
    }

    @Override
    public void doNextImage() {
        repository.nextImage();
    }

    @Override
    public void doSaveImage(ImageGallery image) {
        repository.saveImage(image);
    }
}
