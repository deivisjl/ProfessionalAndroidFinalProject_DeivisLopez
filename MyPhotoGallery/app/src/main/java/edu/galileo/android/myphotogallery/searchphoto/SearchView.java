package edu.galileo.android.myphotogallery.searchphoto;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public interface SearchView {
    void hideInput();
    void showInput();
    void hideProgress();
    void showProgress();

    void onError(String error);
    void onEmpty(String message);
    void setContent(List<FlickrItem> list);
}
