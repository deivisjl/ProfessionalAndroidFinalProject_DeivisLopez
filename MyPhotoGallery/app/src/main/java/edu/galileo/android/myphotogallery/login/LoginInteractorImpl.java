package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class LoginInteractorImpl implements LoginInteractor {
    LoginRepository loginRepository;

    public LoginInteractorImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void checkSession() {
        loginRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        loginRepository.signUp(email,password);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email,password);
    }
}
