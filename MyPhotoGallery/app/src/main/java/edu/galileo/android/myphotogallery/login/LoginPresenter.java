package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface LoginPresenter {
    void onCreate();
    void onDestroy();
    void checkForSession();
    void validateLogin(String email, String password);
    void handleSignUp(String email,String password);

    void onEventMainThread(LoginEvent event);
}
