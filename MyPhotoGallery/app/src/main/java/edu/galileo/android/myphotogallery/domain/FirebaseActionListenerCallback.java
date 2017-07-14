package edu.galileo.android.myphotogallery.domain;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(String error);
}
