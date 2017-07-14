package edu.galileo.android.myphotogallery.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class FlickrService {

    String request;

    public FlickrService(String request) {
        this.request = request;
    }

    private OkHttpClient getFlickrApi(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        HttpUrl originalUrl = originalRequest.url();
                        HttpUrl httpUrl = originalUrl.newBuilder()
                                .addQueryParameter(Flickr.METHOD,Flickr.METHOD_VALUE)
                                .addQueryParameter(Flickr.API_KEY,Flickr.API_KEY_VALUE)
                                .addQueryParameter(Flickr.SEARCH_TEXT,request)
                                .addQueryParameter(Flickr.FORMAT,Flickr.FORMAT_VALUE)
                                .addQueryParameter(Flickr.PAGES,Flickr.PAGES_VALUE)
                                .addQueryParameter(Flickr.CALLBACK_JSON,Flickr.CALLBACK_JSON_VALUE)
                                .build();

                        Request.Builder requestBuilder = originalRequest.newBuilder().url(httpUrl);

                        Request request = requestBuilder.build();

                        return chain.proceed(request);

                    }
                }).build();

        return client;

    }

    public Flickr flickrService(){
        return new Retrofit.Builder()
                .baseUrl(Flickr.BASE_URL)
                .client(getFlickrApi())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Flickr.class);
    }
}
