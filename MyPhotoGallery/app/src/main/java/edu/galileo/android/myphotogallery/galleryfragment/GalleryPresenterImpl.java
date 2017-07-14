package edu.galileo.android.myphotogallery.galleryfragment;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public class GalleryPresenterImpl implements GalleryPresenter {
    EventBus eventBus;
    GalleryView view;
    GalleryInteractor interactor;

    public GalleryPresenterImpl(EventBus eventBus, GalleryView view, GalleryInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getPhotos() {
        if (view != null){
            view.hideRecycler();
            view.showProgress();
        }
        interactor.doGetPhotos();
    }

    @Override
    public void deletePhoto(ImageGallery image) {
        if (view != null){
            view.hideRecycler();
            view.showProgress();
        }
        interactor.doDeletePhoto(image);
    }

    @Override
    @Subscribe
    public void onEventMainThread(GalleryEvent event) {
        switch (event.getType()){
            case GalleryEvent.READ_EVENT:
                onReadEvent(event.getImageList());
                break;
            case GalleryEvent.EMPTY_EVENT:
                onEmptyEvent(event.getMessage());
                break;
            case GalleryEvent.DELETE_EVENT:
                onDeleteEvent(event.getImageList());
                break;
        }
    }

    private void onDeleteEvent(List<ImageGallery> imageList) {
        if (view != null){
            view.hideProgress();
            view.showRecycler();
            view.onDeleteImage(imageList.get(0));
        }
    }

    private void onEmptyEvent(String message) {
        if (view != null){
            view.hideProgress();
            view.showRecycler();
            view.onEmpty(message);
        }
    }

    private void onReadEvent(List<ImageGallery> imageList) {
        if (view != null){
            view.hideProgress();
            view.showRecycler();
            view.setImages(imageList);
        }
    }
}
