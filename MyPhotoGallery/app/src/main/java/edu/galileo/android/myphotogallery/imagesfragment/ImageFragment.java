package edu.galileo.android.myphotogallery.imagesfragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.database.ImageGallery;
import edu.galileo.android.myphotogallery.imagesfragment.swipe.OnSwipeTouchListener;
import edu.galileo.android.myphotogallery.lib.ImageLoader;
import edu.galileo.android.myphotogallery.searchphoto.SearchFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends DialogFragment implements DialogInterface.OnShowListener, ImagesView{


    @Bind(R.id.imgSearch)
    ImageView imgSearch;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.titleTextView)
    TextView title;
    @Bind(R.id.layoutImage)
    RelativeLayout container;

    List<FlickrItem> itemList;
    ImageGallery currentImage;

    @Inject
    ImageLoader loader;
    @Inject
    ImagePresenter presenter;

    MyPhotoGalleryApp app;

    public ImageFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.image_message_title)
                .setPositiveButton(R.string.image_message_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.image_message_next, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_image, null);
        ButterKnife.bind(this, view);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        setupInjection();
        setupGestureDetection();

        return  dialog;
    }

    private void setupGestureDetection() {
        imgSearch.setOnTouchListener(new OnSwipeTouchListener(getContext()){
            @Override
            public void onSwipeRight() {
                presenter.saveImage(currentImage);
                super.onSwipeRight();
            }

            @Override
            public void onSwipeLeft() {
                presenter.saveImage(currentImage);
                super.onSwipeLeft();
            }

            @Override
            public void onSwipeTop() {
                presenter.nextImage();
                super.onSwipeTop();
            }

            @Override
            public void onSwipeBottom() {
                presenter.nextImage();
                super.onSwipeBottom();
            }
        });
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getActivity().getApplication();
        app.getImageComponent(this,this).inject(this);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)getDialog();
        presenter.onShow();
        presenter.setRepo(itemList);
        presenter.nextImage();

        if (dialog != null){
            Button saveButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button nextButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentImage != null){
                        presenter.saveImage(currentImage);
                    }

                }
            });

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.nextImage();
                }
            });
        }
    }

    @Override
    public void setArguments(Bundle args) {

        if (args != null) {
            itemList = args.getParcelableArrayList(SearchFragment.IMAGE_LIST);
        }
    }


    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void showImage() {
        title.setEnabled(true);
        imgSearch.setEnabled(true);
    }

    @Override
    public void hideImage() {
        title.setEnabled(false);
        imgSearch.setEnabled(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSaveImage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        presenter.nextImage();
    }

    @Override
    public void onEndList(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setImageItem(ImageGallery image) {
        this.currentImage = image;
        title.setText(currentImage.getTitle());
        loader.load(imgSearch,currentImage.getImageUrl());
    }
}
