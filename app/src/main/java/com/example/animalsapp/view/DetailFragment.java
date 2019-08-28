package com.example.animalsapp.view;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.animalsapp.R;
import com.example.animalsapp.databinding.FragmentDetailBinding;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.model.AnimalPalette;

public class DetailFragment extends Fragment {

    private AnimalModel animal;

    FragmentDetailBinding animalDetailBinding;

    public DetailFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        animalDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return animalDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            animal = DetailFragmentArgs.fromBundle(getArguments()).getAnimalModel();
        }

        if (animal != null) {
            animalDetailBinding.setAnimal(animal);
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
                                        AnimalPalette animalPalette = new AnimalPalette(intColor);
                                        animalDetailBinding.setPalette(animalPalette);
                                    }
                                });
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }
}
