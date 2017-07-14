package edu.galileo.android.myphotogallery.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryFragment;
import edu.galileo.android.myphotogallery.login.LoginActivity;
import edu.galileo.android.myphotogallery.main.di.MainComponent;
import edu.galileo.android.myphotogallery.mainscreenfragment.MainScreenFragment;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;
import edu.galileo.android.myphotogallery.searchphoto.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    private View viewNav;
    private TextView emailNav;


    private static Uri mediaUri;
    private ProgressDialog progressDialog;
    private static final int CAMERA_WRITE_PERMISSION = 1;
    private static final int PHOTO_REQUEST = 2;


    public static final int MAIN_SCREEN_FRAGMENT = 1;
    public static final int GALLERY_FRAGMENT = 2;
    public static final String FRAGMENT_MAIN_SCREEN = "main_screen_fragment";
    public static final String FRAGMENT_GALLERY = "gallery_fragment";

    @Inject
    MainPresenter mainPresenter;
    @Inject
    SharedPreferences sharedPreferences;

    private MyPhotoGalleryApp app;

    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupInjection();
        setupToolbar();
        mainPresenter.onCreate();

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        showFragment(MAIN_SCREEN_FRAGMENT);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    private void setupToolbar() {
        viewNav = navigationView.getHeaderView(0);
        emailNav = (TextView) viewNav.findViewById(R.id.emailNavTextView);
        String email = sharedPreferences.getString(app.getEmailKey(), getString(R.string.app_name));
        emailNav.setText(email);
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getApplication();
        app.getMainComponent(this).inject(this);
    }

    @OnClick(R.id.fab)
    public void handleFab(){
        takePhoto();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            showFragment(MAIN_SCREEN_FRAGMENT);

        } else if (id == R.id.nav_camera) {
            takePhoto();

        } else if (id == R.id.nav_gallery) {

            showFragment(GALLERY_FRAGMENT);

        }else if(id == R.id.nav_search){

            showDialogSearch();

        } else if (id == R.id.nav_logout) {
            logout();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogSearch() {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.show(getSupportFragmentManager(),getString(R.string.search_message_title));
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    showMessage(PHOTO_REQUEST);
                }else{
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_WRITE_PERMISSION);
                }

            }else{
                createMediaPhoto();
            }
        }
        else{
            createMediaPhoto();
        }
    }

    private void createMediaPhoto() {
        Intent intent;

        mediaUri = getUriFile();
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mediaUri);
        startActivityForResult(intent,PHOTO_REQUEST);
    }

    private void showMessage(final int type) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.main_title_message_camera))
                .setMessage(getString(R.string.main_body_message_camera))
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (type == PHOTO_REQUEST){
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_WRITE_PERMISSION);
                        }else {
                            throw  new IllegalArgumentException();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showSnackbar(R.string.main_result_message_camera);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PHOTO_REQUEST) {
            boolean fromCamera = (data == null || data.getData() == null);
            if (fromCamera) {
                showCenterCrop();
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mediaUri = result.getUri();

                mainPresenter.upPhotos(mediaUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                showSnackbar(result.getError().toString());
            }
        }
    }

    private void showCenterCrop() {
        if (mediaUri != null){
            CropImage.activity(mediaUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setBorderLineColor(Color.RED)
                    .setGuidelinesColor(Color.GREEN)
                    .start(this);
        }else{
            Toast.makeText(MainActivity.this, R.string.main_message_uri_empty, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults .length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (requestCode == CAMERA_WRITE_PERMISSION){
                createMediaPhoto();
            }
        }
    }

    private void logout() {
        mainPresenter.logout();
        sharedPreferences.edit().clear();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showFragment(int fragment) {

        FragmentTransaction fragmentTransaction;
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragment == MAIN_SCREEN_FRAGMENT){

            fragmentTransaction = fragmentManager.beginTransaction();
            MainScreenFragment mainScreenFragment = new MainScreenFragment();
            fragmentTransaction.replace(R.id.contentFrameLayout,mainScreenFragment,FRAGMENT_MAIN_SCREEN);
            fragmentTransaction.commit();

        }else if(fragment == GALLERY_FRAGMENT){

            fragmentTransaction = fragmentManager.beginTransaction();
            GalleryFragment galleryFragment = new GalleryFragment();
            fragmentTransaction.replace(R.id.contentFrameLayout,galleryFragment,FRAGMENT_GALLERY);
            fragmentTransaction.commit();

        }
    }

    private Uri getUriFile(){
        File photoFile = null;
        String timeStamp = new SimpleDateFormat("yyyMMdd_HH:mm:ss").format(new Date());
        String imageFileName = "JPEG_"+timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
            MediaScannerConnection.scanFile(this, new String[] { photoFile.getPath() }, new String[] { "image/jpeg","video/mp4" }, null);
        }catch (IOException e) {
            showSnackbar(R.string.main_error_dispatch_camera);
        }
        return Uri.fromFile(photoFile);
    }

    private void showSnackbar(int res) {
        Snackbar.make(drawer,res,Snackbar.LENGTH_SHORT).show();
    }

    private void showSnackbar(String msg) {
        Snackbar.make(drawer,msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.main_message_post_camera));
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void photoError(String error) {
        showSnackbar(error);
    }

    @Override
    public void photoSuccess() {
        showSnackbar(R.string.main_camera_success);
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDestroy();
        super.onDestroy();
    }

}
