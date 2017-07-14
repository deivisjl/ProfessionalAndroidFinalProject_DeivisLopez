package edu.galileo.android.myphotogallery.api.entities;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class Photo<T> {
    private int perpage;
    private T photo;

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public T getPhoto() {
        return photo;
    }

    public void setPhoto(T photo) {
        this.photo = photo;
    }
}
