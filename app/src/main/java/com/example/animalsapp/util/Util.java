package com.example.animalsapp.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.animalsapp.R;

public class Util {
    public static void loadImage(ImageView imageView, String url, CircularProgressDrawable circularProgressDrawable) {
        RequestOptions options = new RequestOptions()
                .placeholder(circularProgressDrawable)
                .error(R.mipmap.ic_animals_round);

        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(options)
                .load(url)
                .into(imageView);
    }

    public static CircularProgressDrawable getProgressDrawable(Context context) {
        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(context);
        progressDrawable.setCenterRadius(50f);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.start();
        return progressDrawable;
    }

    @BindingAdapter("android:imageURL")
    public static void loadImge(ImageView view, String url) {
        loadImage(view, url, Util.getProgressDrawable(view.getContext()));
    }
}
