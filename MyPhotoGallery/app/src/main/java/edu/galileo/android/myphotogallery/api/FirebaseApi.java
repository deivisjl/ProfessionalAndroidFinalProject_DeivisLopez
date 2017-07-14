package edu.galileo.android.myphotogallery.api;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import edu.galileo.android.myphotogallery.domain.FirebaseActionListenerCallback;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class FirebaseApi {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;

    public static final String PHOTO_PATH = "foto";
    public static final String FILE_PATH = "image";

    public FirebaseApi(DatabaseReference databaseReference, StorageReference storageReference, FirebaseAuth firebaseAuth) {
        this.databaseReference = databaseReference;
        this.storageReference = storageReference;
        this.firebaseAuth = firebaseAuth;
    }

    private DatabaseReference getDatabaseReference(){
        return databaseReference;
    }
    private StorageReference getStorageReference(){
        return storageReference;
    }
    public FirebaseAuth getFirebaseAuth(){return this.firebaseAuth;}

    public DatabaseReference getPhotoDatabaseReference(){
        return getDatabaseReference().getRoot().child(PHOTO_PATH);
    }
    public StorageReference getFileStorageReference(){
        return getStorageReference().getRoot().child(FILE_PATH);
    }

    public String getAuthUserEmail(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = null;
        if (user != null){
            email = user.getEmail();
        }
        return email;
    }

    public void logOut(){
        firebaseAuth.signOut();
    }

    public void checkForSession(FirebaseActionListenerCallback listenerCallback){
        if (firebaseAuth.getCurrentUser() != null){
            listenerCallback.onSuccess();
        }else{
            listenerCallback.onError(null);
        }
    }
    public String getAuthUserKey(){
        FirebaseUser userKey = firebaseAuth.getCurrentUser();
        String userUid = null;
        if(userKey != null){
            userUid = userKey.getUid();
        }
        return userUid;
    }
}
