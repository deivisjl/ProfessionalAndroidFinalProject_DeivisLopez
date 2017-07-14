package edu.galileo.android.myphotogallery.searchphoto;

import java.util.ArrayList;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class SearchEvent {
    public static final int onRequestSuccess = 1;
    public static final int onRequestError = 2;
    public static final int onRequestEmpty = 3;

    private int type;
    private String message;
    private ArrayList<FlickrItem> list;

    public ArrayList<FlickrItem> getList() {
        return list;
    }

    public void setList(ArrayList<FlickrItem> list) {
        this.list = list;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
