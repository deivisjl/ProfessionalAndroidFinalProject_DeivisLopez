package edu.galileo.android.myphotogallery.imagesfragment.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.api.di.ApiModule;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;

/**
 * Created by SAMSUNG on 21/06/2017.
 */
@Singleton
@Component(modules={ImageModule.class, LibsModule.class,ApiModule.class, MyPhotoGalleryAppModule.class})
public interface ImagesComponent {
    void inject(ImageFragment fragment);
}
