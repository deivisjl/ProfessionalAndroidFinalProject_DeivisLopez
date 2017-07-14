package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();
}
