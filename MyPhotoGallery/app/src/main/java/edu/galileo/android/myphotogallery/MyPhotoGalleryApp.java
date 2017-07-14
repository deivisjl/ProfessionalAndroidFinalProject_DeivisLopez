package edu.galileo.android.myphotogallery;

import android.app.Application;
import android.app.Fragment;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.raizlabs.android.dbflow.config.FlowManager;

import edu.galileo.android.myphotogallery.api.di.ApiModule;
import edu.galileo.android.myphotogallery.detailfragment.DetailFragment;
import edu.galileo.android.myphotogallery.detailfragment.di.DaggerDetailComponent;
import edu.galileo.android.myphotogallery.detailfragment.di.DetailComponent;
import edu.galileo.android.myphotogallery.detailfragment.di.DetailModule;
import edu.galileo.android.myphotogallery.di.MyPhotoGalleryAppModule;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryFragment;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryView;
import edu.galileo.android.myphotogallery.galleryfragment.adapter.OnImageItemClickListener;
import edu.galileo.android.myphotogallery.galleryfragment.di.DaggerGalleryComponent;
import edu.galileo.android.myphotogallery.galleryfragment.di.GalleryComponent;
import edu.galileo.android.myphotogallery.galleryfragment.di.GalleryModule;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;
import edu.galileo.android.myphotogallery.imagesfragment.ImagesView;

import edu.galileo.android.myphotogallery.imagesfragment.di.DaggerImagesComponent;
import edu.galileo.android.myphotogallery.imagesfragment.di.ImageModule;
import edu.galileo.android.myphotogallery.imagesfragment.di.ImagesComponent;
import edu.galileo.android.myphotogallery.lib.di.LibsModule;
import edu.galileo.android.myphotogallery.login.LoginView;


import edu.galileo.android.myphotogallery.login.di.DaggerLoginComponent;
import edu.galileo.android.myphotogallery.login.di.LoginComponent;
import edu.galileo.android.myphotogallery.login.di.LoginModule;
import edu.galileo.android.myphotogallery.main.MainView;


import edu.galileo.android.myphotogallery.main.di.DaggerMainComponent;
import edu.galileo.android.myphotogallery.main.di.MainComponent;
import edu.galileo.android.myphotogallery.main.di.MainModule;

import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenFragment;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenView;
import edu.galileo.android.myphotogallery.mainscreenfragment.di.DaggerMainScreenComponent;
import edu.galileo.android.myphotogallery.mainscreenfragment.di.MainScreenComponent;
import edu.galileo.android.myphotogallery.mainscreenfragment.di.MainScreenModule;
import edu.galileo.android.myphotogallery.searchphoto.SearchView;

import edu.galileo.android.myphotogallery.searchphoto.di.DaggerSearchComponent;
import edu.galileo.android.myphotogallery.searchphoto.di.SearchComponent;
import edu.galileo.android.myphotogallery.searchphoto.di.SearchModule;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class MyPhotoGalleryApp extends Application{

    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    private MyPhotoGalleryAppModule myPhotoGalleryAppModule;
    private ApiModule apiModule;

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
        initDB();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBdown();
    }

    private void DBdown() {
        FlowManager.destroy();
    }

    private void initDB() {
        FlowManager.init(this);
    }

    private void initModules() {
        myPhotoGalleryAppModule = new MyPhotoGalleryAppModule(this);
        apiModule = new ApiModule(databaseReference,storageReference,firebaseAuth);
    }

    private void initFirebase() {
        if (!FirebaseApp.getApps(this).isEmpty()){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static String getEmailKey() {
        return EMAIL_KEY;
    }

    public static String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .apiModule(apiModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view){
        return DaggerMainComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .apiModule(apiModule)
                .libsModule(new LibsModule(null))
                .mainModule(new MainModule(view))
                .build();
    }
    public SearchComponent getSearchComponent(SearchView view){

        return DaggerSearchComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .apiModule(apiModule)
                .libsModule(new LibsModule(null))
                .searchModule(new SearchModule(view))
                .build();
    }
    public ImagesComponent getImageComponent(ImagesView view, ImageFragment fragment){

        return DaggerImagesComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .apiModule(apiModule)
                .libsModule(new LibsModule(null))
                .imageModule(new ImageModule(fragment,view))
                .build();
    }
    public DetailComponent getDetailComponent(DetailFragment fragment){
        return DaggerDetailComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .detailModule(new DetailModule(fragment))
                .build();
    }

    public GalleryComponent getGalleryComponent(GalleryView view, GalleryFragment fragment,
                                                OnImageItemClickListener listener){
        return DaggerGalleryComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .libsModule(new LibsModule(null))
                .galleryModule(new GalleryModule(view,fragment,listener))
                .build();
    }
    public MainScreenComponent getMainScreenComponent(MainScreenFragment fragment, MainScreenView view){
        return DaggerMainScreenComponent
                .builder()
                .myPhotoGalleryAppModule(myPhotoGalleryAppModule)
                .apiModule(apiModule)
                .libsModule(new LibsModule(null))
                .mainScreenModule(new MainScreenModule(view,fragment))
                .build();

    }
}
