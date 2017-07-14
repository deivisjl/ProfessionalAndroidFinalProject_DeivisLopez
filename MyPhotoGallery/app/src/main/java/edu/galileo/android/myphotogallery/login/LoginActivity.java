package edu.galileo.android.myphotogallery.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.main.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @Bind(R.id.editTxtEmail)
    EditText editTxtEmail;
    @Bind(R.id.editTxtPassword)
    EditText editTxtPassword;
    @Bind(R.id.btnSignin)
    Button btnSignin;
    @Bind(R.id.btnSignup)
    Button btnSignup;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.loginContainer)
    RelativeLayout loginContainer;

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    SharedPreferences sharedPreferences;

    MyPhotoGalleryApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        app = (MyPhotoGalleryApp) getApplication();

        setupInjection();
        loginPresenter.onCreate();
        loginPresenter.checkForSession();
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }
    @OnClick(R.id.btnSignin)
    public void handleSignIn(){
        loginPresenter.validateLogin(
                editTxtEmail.getText().toString().trim(),
                    editTxtPassword.getText().toString().trim());
    }

    @OnClick(R.id.btnSignup)
    public void handleSignUp(){
        loginPresenter.handleSignUp(
                editTxtEmail.getText().toString().trim(),
                editTxtPassword.getText().toString().trim());
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void enabledInputs() {
        setInputs(true);
    }

    private void setInputs(boolean b) {
        editTxtEmail.setEnabled(b);
        editTxtPassword.setEnabled(b);
        btnSignin.setEnabled(b);
        btnSignup.setEnabled(b);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void setCurrentUser(String email) {
        if (email != null) {
            String key = app.getEmailKey();
            sharedPreferences.edit().putString(key,email).commit();
        }
    }

    @Override
    public void loginError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        editTxtPassword.setError(msgError);
    }

    @Override
    public void navigateToMainScreen() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(loginContainer, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        editTxtPassword.setError(msgError);
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
