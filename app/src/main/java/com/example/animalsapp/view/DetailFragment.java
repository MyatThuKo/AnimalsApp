package com.example.animalsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animalsapp.R;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailFragment extends Fragment {

    private AnimalModel animal;

    @BindView(R.id.animalImage)
    ImageView animalImage;

    @BindView(R.id.animalName)
    TextView animalName;

    @BindView(R.id.animalLocation)
    TextView animalLocation;

    @BindView(R.id.animalLifespan)
    TextView animalLifeSpan;

    @BindView(R.id.animalDiet)
    TextView animalDiet;

    @BindView(R.id.animalLinearLayout)
    LinearLayout animalLayout;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            animal = DetailFragmentArgs.fromBundle(getArguments()).getAnimalModel();
        }

        if (animal != null) {
            animalName.setText(animal.name);
            animalLocation.setText(animal.location);
            animalDiet.setText(animal.diet);
            animalLifeSpan.setText(animal.lifeSpan);

            Util.loadImage(animalImage, animal.imageURL, Util.getProgressDrawable(animalImage.getContext()));
            setUpBackgroundColor(animal.imageURL);
        }
    }

    private void setUpBackgroundColor(String imageURL) {
        Glide.with(this)
                .asBitmap()
                .load(imageURL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource)
                                .generate(palette -> {
                                    Palette.Swatch color = palette.getDominantSwatch();
                                    if (color != null) {
                                        int intColor = color.getRgb();
                                        animalLayout.setBackgroundColor(intColor);
                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
