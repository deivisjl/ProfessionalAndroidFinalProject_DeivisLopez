package edu.galileo.android.myphotogallery.detailfragment.di;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.detailfragment.DetailFragment;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;
import edu.galileo.android.myphotogallery.lib.GlideImageLoader;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
@Module
public class DetailModule {
    private DetailFragment fragment;

    public DetailModule(DetailFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(DetailFragment fragment){return Glide.with(fragment);}

    @Provides
    @Singleton
    DetailFragment providesFragment(){
        return this.fragment;
    }
}
