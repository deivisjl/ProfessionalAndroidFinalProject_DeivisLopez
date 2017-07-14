package edu.galileo.android.myphotogallery.mainscreenfragment;

/**
 * Created by SAMSUNG on 23/06/2017.
 */
public class MainScreenInteractorImpl implements MainScreenInteractor {
    MainScreenRepository repository;

    public MainScreenInteractorImpl(MainScreenRepository repository) {
        this.repository = repository;
    }


    @Override
    public void doOnSubscribe() {
        repository.onSubscribe();
    }

    @Override
    public void doUnSubscribe() {
        repository.unSubscribe();
    }
}
