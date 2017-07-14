package edu.galileo.android.myphotogallery.mainscreenfragment.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.api.di.ApiModule;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenFragment;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
@Singleton
@Component(modules={MainScreenModule.class, LibsModule.class, ApiModule.class, MyPhotoGalleryAppModule.class})
public interface MainScreenComponent {
    void inject(MainScreenFragment fragment);
}
