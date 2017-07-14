package edu.galileo.android.myphotogallery.searchphoto;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.myphotogallery.MyPhotoGalleryApp;
import edu.galileo.android.myphotogallery.R;
import edu.galileo.android.myphotogallery.api.entities.FlickrItem;
import edu.galileo.android.myphotogallery.imagesfragment.ImageFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DialogFragment implements AlertDialog.OnShowListener,
                                                                        SearchView{


    public static final String IMAGE_LIST = "list_image";
    private static final String IMAGE_FRAGMENT = "image_fragment";

    @Bind(R.id.searchTextView)
    EditText searchTxtView;
    @Bind(R.id.placeholderSearchLayout)
    RelativeLayout placeholderSearchLayout;
    @Bind(R.id.progressBarSearch)
    ProgressBar progressBar;

    @Inject
    SearchPresenter presenter;

    private MyPhotoGalleryApp app;

    public SearchFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.search_message_title)
                .setPositiveButton(R.string.search_message_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.search_message_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);

        setupInjection();

        return dialog;
    }

    private void setupInjection() {
        app = (MyPhotoGalleryApp) getActivity().getApplication();
        app.getSearchComponent(this).inject(this);
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)getDialog();

        presenter.onShow();

        if (dialog != null){
            Button searchButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            Button cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.onSearchPhotos(searchTxtView.getText().toString());
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    @Override
    public void hideInput() {
        searchTxtView.setEnabled(false);
    }

    @Override
    public void showInput() {
        searchTxtView.setEnabled(true);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onError(String error) {
        showToast(error);
    }

    private void showToast(String msj) {
        Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmpty(String message) {
        showToast(message);
    }

    @Override
    public void setContent(List<FlickrItem> list) {

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IMAGE_LIST, (ArrayList<? extends Parcelable>) list);

        ImageFragment imageFragment = new ImageFragment();
        imageFragment.setArguments(bundle);
        imageFragment.show(getActivity().getSupportFragmentManager(),IMAGE_FRAGMENT);

        dismiss();
    }
}
