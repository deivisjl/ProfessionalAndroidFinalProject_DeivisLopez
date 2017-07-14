package edu.galileo.android.myphotogallery.login.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.login.LoginInteractor;
import edu.galileo.android.myphotogallery.login.LoginInteractorImpl;
import edu.galileo.android.myphotogallery.login.LoginPresenter;
import edu.galileo.android.myphotogallery.login.LoginPresenterImpl;
import edu.galileo.android.myphotogallery.login.LoginRepository;
import edu.galileo.android.myphotogallery.login.LoginRepositoryImpl;
import edu.galileo.android.myphotogallery.login.LoginView;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Module
public class LoginModule {
    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView view, LoginInteractor interactor){
        return new LoginPresenterImpl(eventBus,view,interactor);
    }

    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository loginRepository){
        return new LoginInteractorImpl(loginRepository);
    }
    @Provides
    @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseApi firebaseApi){
        return new LoginRepositoryImpl(eventBus,firebaseApi);
    }

}
