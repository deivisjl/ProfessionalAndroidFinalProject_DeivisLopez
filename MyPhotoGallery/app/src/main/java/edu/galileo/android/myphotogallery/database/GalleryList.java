package edu.galileo.android.myphotogallery.database;

import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;

/**
 * Created by SAMSUNG on 21/06/2017.
 */
public class GalleryList {

    public static final GalleryList galleryList = new GalleryList();
    public List<FlickrItem> responseList;

    public GalleryList() {
    }

    public List<FlickrItem> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<FlickrItem> responseList) {
        this.responseList = responseList;
    }

    public static GalleryList Instance(){
        return galleryList;
    }
}
