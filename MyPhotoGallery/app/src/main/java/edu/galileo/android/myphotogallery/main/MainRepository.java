package edu.galileo.android.myphotogallery.main;

import android.net.Uri;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public interface MainRepository {
    void upPhoto(Uri image);
    void logout();
}
