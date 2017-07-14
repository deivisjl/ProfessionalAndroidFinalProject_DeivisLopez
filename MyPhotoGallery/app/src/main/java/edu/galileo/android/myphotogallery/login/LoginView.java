package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface LoginView {
    void showProgress();
    void hideProgress();
    void enabledInputs();
    void disableInputs();

    void setCurrentUser(String email);
    void loginError(String error);
    void navigateToMainScreen();

    void newUserSuccess();
    void newUserError(String error);
}
