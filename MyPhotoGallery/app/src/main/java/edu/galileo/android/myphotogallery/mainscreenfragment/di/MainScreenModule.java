package edu.galileo.android.myphotogallery.mainscreenfragment.di;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.lib.GlideImageLoader;
import edu.galileo.android.myphotogallery.lib.ImageLoader;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenFragment;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenInteractor;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenInteractorImpl;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenPresenter;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenPresenterImpl;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenRepository;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenRepositoryImpl;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenView;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
@Module
public class MainScreenModule {
    private MainScreenView view;
    private MainScreenFragment fragment;

    public MainScreenModule(MainScreenView view, MainScreenFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    MainScreenView providesMainScreenView(){
        return  this.view;
    }

    @Provides
    @Singleton
    MainScreenPresenter providesMainScreenPresenter(EventBus eventBus, MainScreenView view, MainScreenInteractor interactor){
        return new MainScreenPresenterImpl(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    MainScreenInteractor providesMainScreenInteractor(MainScreenRepository repository){
        return new MainScreenInteractorImpl(repository);
    }

    @Provides
    @Singleton
    MainScreenRepository providesMainScreenRepository(EventBus eventBus, FirebaseApi firebaseApi){
        return new MainScreenRepositoryImpl(eventBus, firebaseApi);
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(MainScreenFragment fragment){return Glide.with(fragment);}

    @Provides
    @Singleton
    MainScreenFragment providesFragment(){
        return this.fragment;
    }
}
