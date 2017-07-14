package edu.galileo.android.myphotogallery.main;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public interface MainView {
    void showProgress();
    void hideProgress();

    void photoError(String error);
    void photoSuccess();
}
