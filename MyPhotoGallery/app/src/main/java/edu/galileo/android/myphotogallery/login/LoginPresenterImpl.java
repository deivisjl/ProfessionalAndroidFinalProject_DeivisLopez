package edu.galileo.android.myphotogallery.login;


import org.greenrobot.eventbus.Subscribe;

import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class LoginPresenterImpl implements LoginPresenter {

    EventBus eventBus;
    LoginView view;
    LoginInteractor interactor;

    public LoginPresenterImpl(EventBus eventBus, LoginView view, LoginInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void checkForSession() {
        if(view != null){
            view.showProgress();
            view.disableInputs();
        }
        interactor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if(view != null){
            view.showProgress();
            view.disableInputs();
        }
        interactor.doSignIn(email,password);
    }

    @Override
    public void handleSignUp(String email, String password) {
        if(view != null){
            view.showProgress();
            view.disableInputs();
        }
        interactor.doSignUp(email,password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        switch (event.getType()){
            case LoginEvent.onLoginSuccess:
                onLoginSuccess(event.getEmail());
                break;
            case LoginEvent.onLoginError:
                onLoginError(event.getMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedSession();
                break;
            case LoginEvent.onRegisterSuccess:
                onRegisterSuccess();
                break;
            case LoginEvent.onRegisterError:
                onRegisterError(event.getMessage());
                break;
        }
    }

    private void onRegisterError(String message) {
        if (view != null){
            view.hideProgress();
            view.enabledInputs();
            view.newUserError(message);
        }
    }

    private void onRegisterSuccess() {
        if (view != null){
            view.newUserSuccess();
        }
    }

    private void onFailedSession() {
        if (view != null){
            view.hideProgress();
            view.enabledInputs();
        }
    }

    private void onLoginError(String message) {
        if (view != null){
            view.hideProgress();
            view.enabledInputs();
            view.loginError(message);
        }
    }

    private void onLoginSuccess(String email) {
        if (view != null){
            view.setCurrentUser(email);
            view.hideProgress();
            view.navigateToMainScreen();
        }
    }
}
