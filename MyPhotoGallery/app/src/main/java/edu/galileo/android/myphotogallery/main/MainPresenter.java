package edu.galileo.android.myphotogallery.main;

import android.net.Uri;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();
    void upPhotos(Uri image);
    void logout();

    void onEventMainThread(MainEvent event);
}
