package edu.galileo.android.myphotogallery.searchphoto;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class SearchInteractorImpl implements SearchInteractor {
    SearchRepository repository;

    public SearchInteractorImpl(SearchRepository repository) {
        this.repository = repository;
    }

    @Override
    public void doSearchPhotos(String request) {
        repository.getPhotos(request);
    }
}
