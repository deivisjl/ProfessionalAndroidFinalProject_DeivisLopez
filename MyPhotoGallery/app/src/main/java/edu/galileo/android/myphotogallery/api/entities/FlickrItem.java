package edu.galileo.android.myphotogallery.api.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SAMSUNG on 19/06/2017.
 */
public class FlickrItem implements Parcelable{
    private long id;
    private String secret;
    private int server;
    private int farm;
    private String title;

    protected FlickrItem(Parcel in) {
        id = in.readLong();
        secret = in.readString();
        server = in.readInt();
        farm = in.readInt();
        title = in.readString();
    }

    public static final Creator<FlickrItem> CREATOR = new Creator<FlickrItem>() {
        @Override
        public FlickrItem createFromParcel(Parcel in) {
            return new FlickrItem(in);
        }

        @Override
        public FlickrItem[] newArray(int size) {
            return new FlickrItem[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeString(secret);
        dest.writeInt(server);
        dest.writeInt(farm);
        dest.writeString(title);
    }
}
