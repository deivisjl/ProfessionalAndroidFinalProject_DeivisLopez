package edu.galileo.android.myphotogallery.mainscreenfragment;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public class MainScreenPresenterImpl implements MainScreenPresenter {
    private EventBus eventBus;
    private MainScreenView view;
    private MainScreenInteractor interactor;

    public MainScreenPresenterImpl(EventBus eventBus, MainScreenView view, MainScreenInteractor interactor) {
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
    public void onSubscribe() {
        if (view != null){
            view.showProgress();
            view.hideImage();
        }
        interactor.doOnSubscribe();
    }

    @Override
    public void unSubscribe() {
        interactor.doUnSubscribe();
    }


    @Override
    @Subscribe
    public void onEventMainThread(MainScreenEvent event) {
        switch (event.getType()){
            case MainScreenEvent.onReadPhoto:
                onReadPhoto(event.getImage());
                break;
            case MainScreenEvent.onPhotoEmpty:
                onPhotoEmpty(event.getMessage());
                break;
            case MainScreenEvent.onPhotoError:
                onPhotoError(event.getMessage());
                break;
        }
    }

    private void onPhotoError(String message) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.onError(message);
        }
    }

    private void onPhotoEmpty(String message) {
        if (view != null){
            view.hideProgress();
            view.setMessage(message);
        }
    }

    private void onReadPhoto(MyImage image) {
        if (view != null){
            view.hideProgress();
            view.showImage();
            view.setPhoto(image);
        }
    }
}
