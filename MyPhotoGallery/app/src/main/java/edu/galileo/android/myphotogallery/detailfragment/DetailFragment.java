package edu.galileo.android.myphotogallery.detailfragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.galleryfragment.GalleryFragment;
import edu.galileo.android.myphotogallery.lib.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends DialogFragment {


    @Bind(R.id.imgDetail)
    ImageView imgDetail;

    @Inject
    ImageLoader loader;

    String url;

    private MyPhotoGalleryApp app;

    public DetailFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_detail,null);
        ButterKnife.bind(this, view);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        setupInjection();
        showImage();
        return dialog;
    }

    private void showImage() {
        loader.load(imgDetail,url);
    }

    @Override
    public void setArguments(Bundle args) {
        //super.setArguments(args);
        if (args != null){
            url = args.getString(GalleryFragment.IMAGE_DETAIL);
        }
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getActivity().getApplication();
        app.getDetailComponent(this).inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
