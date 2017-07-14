package edu.galileo.android.myphotogallery.main;

import android.net.Uri;

import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public class MainPresenterImpl implements MainPresenter {
    private EventBus eventBus;
    private MainInteractor interactor;
    private MainView view;

    public MainPresenterImpl(EventBus eventBus, MainInteractor interactor, MainView view) {
        this.eventBus = eventBus;
        this.interactor = interactor;
        this.view = view;
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
    public void upPhotos(Uri image) {
        if (view != null){
            view.showProgress();
        }
        interactor.doUpPhotos(image);
    }

    @Override
    public void logout() {
        interactor.doLogOut();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {
        switch (event.getType()){
            case MainEvent.onPhotoSuccess:
                onPhotoSuccess();
                break;
            case MainEvent.onPhotoError:
                onPhotoError(event.getMessage());
                break;

        }
    }

    private void onPhotoError(String message) {
        if (view != null){
            view.hideProgress();
            view.photoError(message);
        }
    }

    private void onPhotoSuccess() {
        if (view != null){
            view.hideProgress();
        }
    }
}
