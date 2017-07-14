package edu.galileo.android.myphotogallery.searchphoto;

import android.util.Log;

import java.util.ArrayList;

import edu.galileo.android.myphotogallery.api.FlickrService;
import edu.galileo.android.myphotogallery.api.entities.Basic;
import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.api.entities.Photo;
import edu.galileo.android.myphotogallery.lib.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class SearchRepositoryImpl implements SearchRepository {

    public static final int SUCCESS_CODE = 200;
    private static final String TAG = SearchRepositoryImpl.class.getSimpleName();
    private ArrayList<FlickrItem> flickrItemList;

    EventBus eventBus;
    FlickrService flickrService;

    public SearchRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPhotos(String request) {

        if (request.equals("")){

            post(SearchEvent.onRequestEmpty,"No se encontraron resultados");

        }else{
            flickrService = new FlickrService(request);

            Call<Basic<Photo<ArrayList<FlickrItem>>>> flickrCall = flickrService.flickrService().getItem();

            flickrCall.enqueue(new Callback<Basic<Photo<ArrayList<FlickrItem>>>>() {
                @Override
                public void onResponse(Call<Basic<Photo<ArrayList<FlickrItem>>>> call, Response<Basic<Photo<ArrayList<FlickrItem>>>> response) {
                    if(response.code() == SUCCESS_CODE) {
                        flickrItemList = response.body().getPhotos().getPhoto();
                        if (flickrItemList.size() > 0){
                            post(flickrItemList);
                        }else{
                            post(SearchEvent.onRequestEmpty,"No se encontraron resultados");
                        }

                    }else{
                        post(SearchEvent.onRequestError,"Ocurrió un error en la respuesta");
                    }

                }

                @Override
                public void onFailure(Call<Basic<Photo<ArrayList<FlickrItem>>>> call, Throwable t) {
                    post(SearchEvent.onRequestError,t.getLocalizedMessage());
                    Log.d(TAG,t.getLocalizedMessage());
                }
            });
        }

    }

    public  void post(int type, String message, ArrayList<FlickrItem> itemList){
        SearchEvent event = new SearchEvent();
        event.setMessage(message);
        event.setType(type);
        event.setList(itemList);
        eventBus.post(event);
    }

    private void post(ArrayList<FlickrItem> itemList) {
        post(SearchEvent.onRequestSuccess,null,itemList);
    }

    private void post(int type, String message){
        post(type,message,null);
    }


}
