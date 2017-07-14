package edu.galileo.android.myphotogallery.mainscreenfragment;

import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public interface MainScreenView {
    void showImage();
    void hideImage();
    void showProgress();
    void hideProgress();

    void onError(String message);
    void setPhoto(MyImage image);
    void setMessage(String message);
}
