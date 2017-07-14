package edu.galileo.android.myphotogallery.main;

import android.net.Uri;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public class MainInteractorImpl implements MainInteractor {
    private MainRepository repository;

    public MainInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doUpPhotos(Uri image) {
        repository.upPhoto(image);
    }

    @Override
    public void doLogOut() {
        repository.logout();
    }
}
