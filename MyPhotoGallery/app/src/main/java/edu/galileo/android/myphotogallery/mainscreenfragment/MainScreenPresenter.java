package edu.galileo.android.myphotogallery.mainscreenfragment;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public interface MainScreenPresenter {
    void onCreate();
    void onDestroy();
    void onSubscribe();
    void unSubscribe();

    void onEventMainThread(MainScreenEvent event);
}
