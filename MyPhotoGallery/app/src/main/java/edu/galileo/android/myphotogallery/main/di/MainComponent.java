package edu.galileo.android.myphotogallery.main.di;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.api.di.ApiModule;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;
import edu.galileo.android.myphotogallery.main.MainActivity;
import edu.galileo.android.myphotogallery.main.MainPresenter;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
@Singleton
@Component(modules={MainModule.class, LibsModule.class,ApiModule.class, MyPhotoGalleryAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
