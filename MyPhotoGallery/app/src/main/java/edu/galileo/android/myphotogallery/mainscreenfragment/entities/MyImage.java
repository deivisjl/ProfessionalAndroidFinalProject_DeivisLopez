package edu.galileo.android.myphotogallery.mainscreenfragment.entities;

/**
 * Created by SAMSUNG on 10/06/2017.
 */
public class MyImage {
    private String imageUrl;
    private String date;
    private String id;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
