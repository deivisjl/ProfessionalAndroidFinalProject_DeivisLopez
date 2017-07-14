package edu.galileo.android.myphotogallery.galleryfragment;

import java.util.List;

import edu.galileo.android.myphotogallery.database.ImageGallery;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public class GalleryEvent {

    public static final int READ_EVENT = 1;
    public static final int DELETE_EVENT = 2;
    public static final int EMPTY_EVENT = 3;

    private int type;
    private String message;
    private List<ImageGallery> imageList;

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

    public List<ImageGallery> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageGallery> imageList) {
        this.imageList = imageList;
    }
}
