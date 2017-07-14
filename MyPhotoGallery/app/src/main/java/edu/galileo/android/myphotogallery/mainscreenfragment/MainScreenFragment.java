package edu.galileo.android.myphotogallery.mainscreenfragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.lib.ImageLoader;
import edu.galileo.android.myphotogallery.mainscreenfragment.entities.MyImage;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainScreenFragment extends Fragment implements MainScreenView {

    @Bind(R.id.photoImageView)
    ImageView photoImageView;
    @Bind(R.id.emptyImageView)
    ImageView emptyImageView;
    @Bind(R.id.wrapperImage)
    LinearLayout wrapperImage;
    @Bind(R.id.emptyTextView)
    TextView emptyTextView;
    @Bind(R.id.wrapperMessage)
    LinearLayout wrapperMessage;
    @Bind(R.id.screenProgressBar)
    ProgressBar screenProgressBar;

    @Inject
    MainScreenPresenter presenter;

    @Inject
    ImageLoader loader;


    private MyPhotoGalleryApp app;

    public MainScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        ButterKnife.bind(this, view);

        setupInjection();
        presenter.onCreate();
        presenter.onSubscribe();

        return view;
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getActivity().getApplication();
        app.getMainScreenComponent(this,this).inject(this);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showImage() {
        photoImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideImage() {
        photoImageView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        screenProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        screenProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPhoto(MyImage image) {
        setImagesEmpty(false);
        loader.load(photoImageView,image.getImageUrl());
    }

    @Override
    public void setMessage(String message) {
        setImagesEmpty(true);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setImagesEmpty(boolean b){
        if (b){
            wrapperImage.setVisibility(View.VISIBLE);
            wrapperMessage.setVisibility(View.VISIBLE);
        }
        else{
            wrapperImage.setVisibility(View.GONE);
            wrapperMessage.setVisibility(View.GONE);
        }

    }
}
