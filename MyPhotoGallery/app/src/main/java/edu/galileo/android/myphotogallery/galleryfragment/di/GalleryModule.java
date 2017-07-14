package edu.galileo.android.myphotogallery.galleryfragment.di;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryFragment;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryInteractor;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryInteractorImpl;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryPresenter;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryPresenterImpl;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryRepository;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryRepositoryImpl;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryView;
import edu.galileo.android.myphotogallery.galleryfragment.adapter.GalleryAdapter;
import edu.galileo.android.myphotogallery.galleryfragment.adapter.OnImageItemClickListener;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.lib.GlideImageLoader;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
@Module
public class GalleryModule {
    private GalleryView view;
    private GalleryFragment fragment;
    private OnImageItemClickListener clickListener;

    public GalleryModule(GalleryView view, GalleryFragment fragment, OnImageItemClickListener clickListener) {
        this.view = view;
        this.fragment = fragment;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    GalleryView providesGalleryView(){
        return this.view;
    }

    @Provides
    @Singleton
    GalleryPresenter providesGalleryPresenter(EventBus eventBus, GalleryView view, GalleryInteractor interactor){
        return new GalleryPresenterImpl(eventBus, view, interactor);
    }

    @Provides
    @Singleton
    GalleryInteractor providesGalleryInteractor(GalleryRepository repository){
        return new GalleryInteractorImpl(repository);
    }

    @Provides
    @Singleton
    GalleryRepository providesGalleryRepository(EventBus eventBus){
        return new GalleryRepositoryImpl(eventBus);
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(GalleryFragment fragment){return Glide.with(fragment);}

    @Provides
    @Singleton
    GalleryFragment providesFragment(){
        return this.fragment;
    }

    @Provides
    @Singleton
    GalleryAdapter providesGalleryAdapter(List<ImageGallery> imageList, ImageLoader loader, OnImageItemClickListener listener){
        return new GalleryAdapter(imageList, loader, listener);
    }

    @Provides
    @Singleton
    OnImageItemClickListener providesClickListener(){
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<ImageGallery> providesEmptyList(){
        return new ArrayList<ImageGallery>();
    }

}
