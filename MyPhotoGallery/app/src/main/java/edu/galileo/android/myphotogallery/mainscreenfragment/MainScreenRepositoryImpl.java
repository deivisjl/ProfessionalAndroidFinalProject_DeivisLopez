package edu.galileo.android.myphotogallery.mainscreenfragment;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.domain.FirebaseActionListenerCallback;
import edu.galileo.android.myphotogallery.domain.FirebaseEventListenerCallback;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public class MainScreenRepositoryImpl implements MainScreenRepository {

    private EventBus eventBus;
    private FirebaseApi firebaseApi;
    private ChildEventListener photoEventListener;
    private Query currentUser;

    private String currentUserId;

    public MainScreenRepositoryImpl(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
        currentUserId = firebaseApi.getAuthUserKey();
    }

    @Override
    public void onSubscribe() {
        checkForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(String error) {
                if(error!=null){
                    post(MainScreenEvent.onPhotoEmpty, error);
                }

            }
        });

        subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot) {
                MyImage image = dataSnapshot.getValue(MyImage.class);
                image.setId(dataSnapshot.getKey());

                post(MainScreenEvent.onReadPhoto,image);
            }

            @Override
            public void onCanceled(DatabaseError databaseError) {
                post(MainScreenEvent.onPhotoError,databaseError.getMessage());
            }
        });
    }

    private void post(int type, String message, MyImage image){
        MainScreenEvent event = new MainScreenEvent();
        event.setType(type);
        event.setMessage(message);
        event.setImage(image);
        eventBus.post(event);
    }

    private void post(int type, String error) {
        post(type,error,null);
    }
    private void post(int type, MyImage image){
        post(type,null,image);
    }

    @Override
    public void unSubscribe() {
        currentUser.removeEventListener(photoEventListener);
    }

    public void checkForData(final FirebaseActionListenerCallback listener){
        ValueEventListener postListener = new ValueEventListener() {
            @Override public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    listener.onSuccess();
                } else {
                    listener.onError("No hay fotos");
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE API", databaseError.getMessage());
            }
        };
        currentUser = firebaseApi.getPhotoDatabaseReference().orderByChild("user").equalTo(currentUserId);
        currentUser.addValueEventListener(postListener);
    }
    public void subscribe(final FirebaseEventListenerCallback listener){
        if (photoEventListener == null){
            photoEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listener.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onCanceled(databaseError);
                }
            };

            currentUser = firebaseApi.getPhotoDatabaseReference().orderByChild("user").equalTo(currentUserId);
            currentUser.addChildEventListener(photoEventListener);

        }
    }
}
