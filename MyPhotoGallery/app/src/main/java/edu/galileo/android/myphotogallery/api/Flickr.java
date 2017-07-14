package edu.galileo.android.myphotogallery.api;

import java.util.ArrayList;

import edu.galileo.android.myphotogallery.api.entities.Basic;
import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.api.entities.Photo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public interface Flickr {

    String BASE_URL = "https://api.flickr.com/";
    String METHOD = "method";
    String METHOD_VALUE = "flickr.photos.search";
    String API_KEY = "api_key";
    String API_KEY_VALUE = "b8f803c8496df5d78bb51fbcb8bbf9e8";
    String SEARCH_TEXT = "text";
    String FORMAT = "format";
    String FORMAT_VALUE = "json";
    String PAGES = "per_page";
    String PAGES_VALUE = "10";
    String CALLBACK_JSON = "nojsoncallback";
    String CALLBACK_JSON_VALUE = "1";

    @GET("services/rest/")
    Call<Basic<Photo<ArrayList<FlickrItem>>>> getItem();
}
