package edu.galileo.android.myphotogallery.lib.di;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.lib.GlideImageLoader;
import edu.galileo.android.myphotogallery.lib.GreenRobotEventBus;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Module
public class LibsModule {
    private Activity activity;

    public LibsModule( Activity activity){this.activity = activity;}

    @Provides
    @Singleton
    Activity providesActivity(){return this.activity;}

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    /*@Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }
    @Provides
    @Singleton
    RequestManager providesRequestManager(Activity activity){
        return Glide.with(activity);
    }*/
}
