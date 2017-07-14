package edu.galileo.android.myphotogallery.galleryfragment;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 22/06/2017.
 */
public class GalleryRepositoryImpl implements GalleryRepository {
    EventBus eventBus;

    public GalleryRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPhotos() {
        FlowCursorList<ImageGallery> storedImages =  new FlowCursorList<ImageGallery>(false, ImageGallery.class);
        post(GalleryEvent.READ_EVENT,storedImages.getAll());
        //int count = storedImages.getCount();
        //ImageGallery lastImage = storedImages.getItem(count);
        storedImages.close();
    }

    @Override
    public void deletePhoto(ImageGallery image) {
        image.delete();
        post(GalleryEvent.DELETE_EVENT, Arrays.asList(image));
    }

    public void post(int type, String message, List<ImageGallery> list){
        GalleryEvent event = new GalleryEvent();
        event.setMessage(message);
        event.setType(type);
        event.setImageList(list);
        eventBus.post(event);
    }

    private void post(int type, List<ImageGallery> list) {
        if (list.size() > 0){
            post(type,null,list);
        }else{
            post(GalleryEvent.EMPTY_EVENT,"No hay imagenes guardadas",null);
        }
    }
}
