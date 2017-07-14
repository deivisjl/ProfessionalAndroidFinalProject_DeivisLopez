package edu.galileo.android.myphotogallery.main;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public class MainRepositoryImpl implements MainRepository {

    private FirebaseApi firebaseApi;
    private EventBus eventBus;

    public MainRepositoryImpl(FirebaseApi firebaseApi, EventBus eventBus) {
        this.firebaseApi = firebaseApi;
        this.eventBus = eventBus;
    }

    @Override
    public void upPhoto(Uri image) {
        final StorageReference filepath = firebaseApi.getFileStorageReference().child(image.getLastPathSegment());

        filepath.putFile(image)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        final String downloadUri = taskSnapshot.getDownloadUrl().toString();

                        final String newPhoto = firebaseApi.getPhotoDatabaseReference().push().getKey();

                        MyImage image = new MyImage();
                        image.setId(newPhoto);
                        image.setUser(firebaseApi.getAuthUserKey());
                        image.setImageUrl(downloadUri);
                        image.setDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));

                        firebaseApi.getPhotoDatabaseReference().child(image.getId()).setValue(image);

                        FlowCursorList<ImageGallery> storedImages =  new FlowCursorList<ImageGallery>(false, ImageGallery.class);
                        int count = storedImages.getCount();
                        storedImages.close();

                        ImageGallery myImage = new ImageGallery();
                        myImage.setImageUrl(downloadUri);
                        myImage.setImageId((count + 1));
                        myImage.setTitle(new SimpleDateFormat("ddMMyyyy").format(new Date()));
                        myImage.save();
                        post(MainEvent.onPhotoSuccess);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        post(MainEvent.onPhotoError,e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void logout() {
        firebaseApi.logOut();
    }

    private void post(int type) {
        post(type,null);
    }
    private void post(int type, String message){
        MainEvent event = new MainEvent();
        event.setType(type);
        event.setMessage(message);
        eventBus.post(event);
    }
}
