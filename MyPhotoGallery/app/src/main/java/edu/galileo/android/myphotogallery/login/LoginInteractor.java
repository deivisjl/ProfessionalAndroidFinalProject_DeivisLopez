package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface LoginInteractor {
    void checkSession();
    void doSignUp(String email, String password);
    void doSignIn(String email, String password);
}
