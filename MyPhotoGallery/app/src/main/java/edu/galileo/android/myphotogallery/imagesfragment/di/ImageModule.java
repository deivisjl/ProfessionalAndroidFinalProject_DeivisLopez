package edu.galileo.android.myphotogallery.imagesfragment.di;

import android.app.Activity;
import android.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;
import edu.galileo.android.myphotogallery.imagesfragment.ImageInteractor;
import edu.galileo.android.myphotogallery.imagesfragment.ImageInteractorImpl;
import edu.galileo.android.myphotogallery.imagesfragment.ImagePresenter;
import edu.galileo.android.myphotogallery.imagesfragment.ImagePresenterImpl;
import edu.galileo.android.myphotogallery.imagesfragment.ImageRepository;
import edu.galileo.android.myphotogallery.imagesfragment.ImageRepositoryImpl;
import edu.galileo.android.myphotogallery.imagesfragment.ImagesView;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.lib.GlideImageLoader;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * Created by SAMSUNG on 21/06/2017.
 */
@Module
public class ImageModule {
    private ImageFragment fragment;
    private ImagesView view;

    public ImageModule(ImageFragment fragment, ImagesView view) {
        this.fragment = fragment;
        this.view = view;
    }

    @Provides
    @Singleton
    ImagesView providesImagesView(){
        return this.view;
    }
    @Provides
    @Singleton
    ImagePresenter providesImagesPresenter(EventBus eventBus, ImagesView view, ImageInteractor interactor){
        return new ImagePresenterImpl(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    ImageInteractor providesImagesInteractor(ImageRepository repository){
        return new ImageInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ImageRepository providesImagesRepository(EventBus eventBus){
        return new ImageRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(ImageFragment fragment){return Glide.with(fragment);}

    @Provides
    @Singleton
    ImageFragment providesFragmente(){
        return this.fragment;
    }
}
