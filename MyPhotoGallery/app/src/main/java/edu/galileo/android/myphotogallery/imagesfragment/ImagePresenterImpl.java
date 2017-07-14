package edu.galileo.android.myphotogallery.imagesfragment;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 21/06/2017.
 */
public class ImagePresenterImpl implements ImagePresenter {
    EventBus eventBus;
    ImagesView view;
    ImageInteractor interactor;

    public ImagePresenterImpl(EventBus eventBus, ImagesView view, ImageInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void setRepo(List<FlickrItem> list) {
        if (view != null){
            view.hideImage();
            view.showProgress();
        }
        interactor.doSetList(list);
    }

    @Override
    public void nextImage() {
        if (view != null){
            view.hideImage();
            view.showProgress();
        }
        interactor.doNextImage();
    }

    @Override
    public void saveImage(ImageGallery image) {
        if (view != null){
            view.hideImage();
            view.showProgress();
        }
        interactor.doSaveImage(image);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ImageEvent event) {
        switch (event.getType()){
            case ImageEvent.onSuccessBuilder:
                onSuccessBuilder(event.getImage());
                break;
            case ImageEvent.onEndList:
                onEndList(event.getMessage());
                break;
            case ImageEvent.onErrorBuilder:
                onErrorBuilder(event.getMessage());
                break;
            case ImageEvent.onSaveImage:
                onSaveImage(event.getMessage());
                break;
        }
    }

    private void onSaveImage(String message) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.onSaveImage(message);
        }
    }

    private void onErrorBuilder(String message) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.onEndList(message);
        }
    }

    private void onEndList(String message) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.onEndList(message);
        }
    }

    private void onSuccessBuilder(ImageGallery image) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.setImageItem(image);
        }
    }
}
