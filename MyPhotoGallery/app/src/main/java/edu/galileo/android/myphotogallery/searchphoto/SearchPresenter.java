package edu.galileo.android.myphotogallery.searchphoto;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public interface SearchPresenter {

    void onShow();
    void onDestroy();
    void onSearchPhotos(String request);

    void onEventMainThread(SearchEvent event);

}
