package edu.galileo.android.myphotogallery.galleryfragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.detailfragment.DetailFragment;
import edu.galileo.android.myphotogallery.galleryfragment.adapter.GalleryAdapter;
import edu.galileo.android.myphotogallery.galleryfragment.adapter.OnImageItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment implements GalleryView, OnImageItemClickListener {

    @Bind(R.id.galleryRecyclerView)
    RecyclerView galleryRecyclerView;
    @Bind(R.id.progressBarGallery)
    ProgressBar progressBarGallery;
    @Bind(R.id.wrapperImageGallery)
    LinearLayout wrapperImageGallery;
    @Bind(R.id.wrapperMessage)
    LinearLayout wrapperMessage;
    @Bind(R.id.containerGallery)
    FrameLayout containerGallery;

    @Inject
    GalleryPresenter presenter;

    @Inject
    GalleryAdapter adapter;



    private MyPhotoGalleryApp app;
    public static final String IMAGE_DETAIL = "image_detail";
    public static final String DETAIL_FRAGMENT = "fragment_detail";

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);
        setupInjection();
        setupAdapter();
        presenter.onCreate();
        presenter.getPhotos();

        return view;
    }

    private void setupAdapter() {
        galleryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        galleryRecyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getActivity().getApplication();
        app.getGalleryComponent(this, this, this).inject(this);
    }

    @Override
    public void showRecycler() {
        galleryRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecycler() {
        galleryRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBarGallery.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBarGallery.setVisibility(View.GONE);
    }

    @Override
    public void setImages(List<ImageGallery> imageList) {
        setEmptyView(false);
        adapter.setImages(imageList);
    }

    @Override
    public void onEmpty(String message) {
        setEmptyView(true);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteImage(ImageGallery image) {
        adapter.deleteImage(image);
        Snackbar.make(containerGallery, getString(R.string.gallery_delete_image), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onImageClick(ImageGallery image) {
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_DETAIL, image.getImageUrl());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        detailFragment.show(getActivity().getSupportFragmentManager(), DETAIL_FRAGMENT);
    }

    @Override
    public void onDeleteClick(ImageGallery image) {
        presenter.deletePhoto(image);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setEmptyView(boolean b) {
        if (!b) {
            wrapperImageGallery.setVisibility(View.GONE);
            wrapperMessage.setVisibility(View.GONE);
        } else {
            galleryRecyclerView.setVisibility(View.GONE);
            wrapperImageGallery.setVisibility(View.VISIBLE);
            wrapperMessage.setVisibility(View.VISIBLE);
        }

    }
}
