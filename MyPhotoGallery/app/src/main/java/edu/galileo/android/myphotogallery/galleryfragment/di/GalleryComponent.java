package edu.galileo.android.myphotogallery.galleryfragment.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryFragment;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
@Singleton
@Component(modules={GalleryModule.class, LibsModule.class, MyPhotoGalleryAppModule.class})
public interface GalleryComponent {
    void inject(GalleryFragment fragment);
}
