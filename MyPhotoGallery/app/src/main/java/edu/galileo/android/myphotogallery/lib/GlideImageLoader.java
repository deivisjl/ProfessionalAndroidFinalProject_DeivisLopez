package edu.galileo.android.myphotogallery.lib;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;
    private RequestListener onFinishedLoadingListener;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void load(ImageView imageView, String URL) {
        if(onFinishedLoadingListener!=null){
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .listener(onFinishedLoadingListener)
                    .into(imageView);
        }else{
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(700,700)
                    .centerCrop()
                    .into(imageView);
        }
    }


}
