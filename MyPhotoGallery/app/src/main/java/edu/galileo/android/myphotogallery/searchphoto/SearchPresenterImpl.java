package edu.galileo.android.myphotogallery.searchphoto;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class SearchPresenterImpl implements SearchPresenter {

    EventBus eventBus;
    SearchView view;
    SearchInteractor interactor;

    public SearchPresenterImpl(EventBus eventBus, SearchView view, SearchInteractor interactor) {
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
    public void onSearchPhotos(String request) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.doSearchPhotos(request);
    }

    @Override
    @Subscribe
    public void onEventMainThread(SearchEvent event) {
        switch(event.getType()){
            case SearchEvent.onRequestEmpty:
                onRequestEmpty(event.getMessage());
                break;
            case SearchEvent.onRequestSuccess:
                onRequestSuccess(event.getList());
                break;
            case SearchEvent.onRequestError:
                onRequestError(event.getMessage());
                break;
        }
    }

    private void onRequestError(String message) {
        if (view != null){
            view.hideProgress();
            view.showInput();
            view.onEmpty(message);
        }
    }

    private void onRequestSuccess(ArrayList<FlickrItem> list) {
        if (view != null){
            view.setContent(list);
        }
    }

    private void onRequestEmpty(String message) {
        if (view != null){
            view.hideProgress();
            view.showInput();
            view.onEmpty(message);
        }
    }

}
