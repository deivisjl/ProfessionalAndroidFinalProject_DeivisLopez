package edu.galileo.android.myphotogallery.login;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class LoginEvent {

    public static final int onLoginSuccess = 1;
    public static final int onLoginError = 2;
    public static final int onRegisterSuccess = 3;
    public static final int onRegisterError = 4;
    public final static int onFailedToRecoverSession = 5;

    private int type;
    private String message;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
