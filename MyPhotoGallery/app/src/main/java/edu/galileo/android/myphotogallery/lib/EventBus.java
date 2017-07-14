package edu.galileo.android.myphotogallery.lib;

/**
 * Created by SAMSUNG on 6/06/2017.
 */
public interface EventBus {
    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);
}
