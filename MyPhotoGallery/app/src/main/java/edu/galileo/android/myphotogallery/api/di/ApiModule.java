package edu.galileo.android.myphotogallery.api.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.api.FirebaseApi;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
@Module
public class ApiModule {

    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;

    public ApiModule(DatabaseReference databaseReference, StorageReference storageReference, FirebaseAuth firebaseAuth) {
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.firebaseAuth = firebaseAuth;
    }
    @Provides
    @Singleton
    FirebaseApi providesFirebaseApi(){
        return new FirebaseApi(databaseReference,storageReference,firebaseAuth);
    }
}
