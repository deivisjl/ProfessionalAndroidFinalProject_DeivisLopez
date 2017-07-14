package edu.galileo.android.myphotogallery.login.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.galileo.android.myphotogallery.api.di.ApiModule;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;
import edu.galileo.android.myphotogallery.login.LoginActivity;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Singleton
@Component(modules={LoginModule.class, ApiModule.class, LibsModule.class, MyPhotoGalleryAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
