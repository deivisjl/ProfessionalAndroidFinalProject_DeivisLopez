package edu.galileo.android.myphotogallery.searchphoto.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.galileo.android.myphotogallery.lib.EventBus;
import edu.galileo.android.myphotogallery.searchphoto.SearchInteractor;
import edu.galileo.android.myphotogallery.searchphoto.SearchInteractorImpl;
import edu.galileo.android.myphotogallery.searchphoto.SearchPresenter;
import edu.galileo.android.myphotogallery.searchphoto.SearchPresenterImpl;
import edu.galileo.android.myphotogallery.searchphoto.SearchRepository;
import edu.galileo.android.myphotogallery.searchphoto.SearchRepositoryImpl;
import edu.galileo.android.myphotogallery.searchphoto.SearchView;

/**
 * Created by SAMSUNG on 20/06/2017.
 */
@Module
public class SearchModule {

    SearchView view;

    public SearchModule(SearchView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    SearchView providesSearchView(){
        return this.view;
    }

    @Provides
    @Singleton
    SearchPresenter providesSearchPresenter(EventBus eventBus, SearchView view, SearchInteractor interactor){
        return new SearchPresenterImpl(eventBus, view, interactor) ;
    }

    @Provides
    @Singleton
    SearchInteractor providesSearchInteractor(SearchRepository repository){
        return new SearchInteractorImpl(repository);
    }

    @Provides
    @Singleton
    SearchRepository providesSearchRepository(EventBus eventBus){
        return  new SearchRepositoryImpl(eventBus);
    }
}
