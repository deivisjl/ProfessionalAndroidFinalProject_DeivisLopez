package edu.galileo.android.myphotogallery.lib.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Singleton
@Component(modules ={LibsModule.class, MyPhotoGalleryAppModule.class})
public interface LibsComponent {
}
