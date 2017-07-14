package edu.galileo.android.myphotogallery.main.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.main.MainInteractor;
import edu.galileo.android.myphotogallery.main.MainInteractorImpl;
import edu.galileo.android.myphotogallery.main.MainPresenter;
import edu.galileo.android.myphotogallery.main.MainPresenterImpl;
import edu.galileo.android.myphotogallery.main.MainRepository;
import edu.galileo.android.myphotogallery.main.MainRepositoryImpl;
import edu.galileo.android.myphotogallery.main.MainView;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
@Module
public class MainModule {
    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    MainView providesMainView(){
        return this.view;
    }

    @Provides
    @Singleton
    MainPresenter providesMainPresenter(EventBus eventBus, MainInteractor interactor, MainView view){
        return new MainPresenterImpl(eventBus, interactor, view);
    }

    @Provides
    @Singleton
    MainInteractor providesMainInteractor(MainRepository repository){
        return new MainInteractorImpl(repository);
    }

    @Provides
    @Singleton
    MainRepository providesMainRepository(FirebaseApi firebaseApi, EventBus eventBus){
        return new MainRepositoryImpl(firebaseApi, eventBus);
    }

}
