package edu.galileo.android.myphotogallery.domain;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public interface FirebaseEventListenerCallback {
    void onChildAdded(DataSnapshot dataSnapshot);
    void onCanceled(DatabaseError databaseError);
}
