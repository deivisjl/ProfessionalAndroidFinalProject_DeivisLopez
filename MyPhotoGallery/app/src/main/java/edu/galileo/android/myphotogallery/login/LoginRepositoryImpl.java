package edu.galileo.android.myphotogallery.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;

import edu.galileo.android.myphotogallery.api.FirebaseApi;
import edu.galileo.android.myphotogallery.domain.FirebaseActionListenerCallback;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class LoginRepositoryImpl implements LoginRepository {
    EventBus eventBus;
    FirebaseApi firebaseApi;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseApi firebaseApi) {
        this.eventBus = eventBus;
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseApi.getFirebaseAuth().createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        postEvent(LoginEvent.onRegisterSuccess);
                        signIn(email,password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.onRegisterError,e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void signIn(String email, String password) {
        firebaseApi.getFirebaseAuth().signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String email = firebaseApi.getAuthUserEmail();
                        postEvent(LoginEvent.onLoginSuccess,email);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        postEvent(LoginEvent.onLoginError,e.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void checkSession() {
        firebaseApi.checkForSession(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                String email = firebaseApi.getAuthUserEmail();
                postEvent(LoginEvent.onLoginSuccess,email);
            }

            @Override
            public void onError(String error) {
                postEvent(LoginEvent.onFailedToRecoverSession);
            }
        });
    }

    private void postEvent(int type, String email) {
        postEvent(type,null,email);
    }
    private void postEvent(int type) {
        postEvent(type,null,null);
    }
    private void postEvent(int type, String error, String email) {
        LoginEvent event = new LoginEvent();
        event.setType(type);
        event.setMessage(error);
        event.setEmail(email);
        eventBus.post(event);
    }


}
