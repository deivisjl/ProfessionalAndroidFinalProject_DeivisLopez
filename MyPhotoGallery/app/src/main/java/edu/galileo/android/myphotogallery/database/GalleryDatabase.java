package edu.galileo.android.myphotogallery.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by SAMSUNG on 12/06/2017.
 */
@Database(name = GalleryDatabase.NAME,version = GalleryDatabase.VERSION)
public class GalleryDatabase {
    public static final int VERSION = 1;
    public static final String NAME = "Gallery";
}
