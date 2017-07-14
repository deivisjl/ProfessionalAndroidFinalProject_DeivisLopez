package edu.galileo.android.myphotogallery.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Module
public class MyPhotoGalleryAppModule {

    MyPhotoGalleryApp app;

    public MyPhotoGalleryAppModule(MyPhotoGalleryApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return app.getApplicationContext();
    }
    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(app.getSharedPreferencesName(), Context.MODE_PRIVATE);
    }
    @Provides
    @Singleton
    Application providesApplication() {
        return this.app;
    }
}
