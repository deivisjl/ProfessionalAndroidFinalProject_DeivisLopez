package edu.galileo.android.myphotogallery.imagesfragment;


import java.util.List;

import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.GalleryList;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.lib.EventBus;

/**
 * Created by SAMSUNG on 20/06/2017.
 */
public class ImageRepositoryImpl implements ImageRepository {

    public static final String BASE_URL = "https://farm";
    public static final String BASE_API = ".staticflickr.com/";
    public static final String EXT = "_m.jpg";
    public static final String SLASH = "/";
    public static final String SEPARATOR = "_";

    EventBus eventBus;
    int cont = 0;

    public ImageRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void setListRepo(List<FlickrItem> listItem) {
        if (listItem.size() > 0){
            GalleryList.galleryList.setResponseList(listItem);
        }
    }

    private ImageGallery setItem(FlickrItem item){
        ImageGallery image = new ImageGallery();
        image.setImageId(item.getId());
        image.setTitle(item.getTitle());
        image.setImageUrl(BASE_URL + item.getFarm() +
                BASE_API + item.getServer() + SLASH +
                item.getId() + SEPARATOR + item.getSecret() + EXT);
        cont++;

        return  image;
    }

    @Override
    public void nextImage() {
        if(cont < GalleryList.galleryList.getResponseList().size()){
        ImageGallery image = setItem(GalleryList.galleryList.getResponseList().get(cont));
            post(ImageEvent.onSuccessBuilder,image);
        }else{
            post(ImageEvent.onEndList,"No hay mas imagenes a mostrar");
        }
    }

    @Override
    public void saveImage(ImageGallery image) {
        image.save();
        post(ImageEvent.onSaveImage,"Imagen guardada correctamente");
    }

    public void post(int type, String message, ImageGallery image) {
        ImageEvent event = new ImageEvent();
        event.setType(type);
        event.setImage(image);
        event.setMessage(message);
        eventBus.post(event);
    }
    private void post(int type, String message){
        post(type,message,null);
    }

    private void post(int type, ImageGallery image){
        post(type,null,image);
    }

}
