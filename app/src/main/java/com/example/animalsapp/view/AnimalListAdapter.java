package com.example.animalsapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalsapp.R;
import com.example.animalsapp.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalListAdapter extends RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder> {

    private ArrayList<AnimalModel> animalList = new ArrayList<>();

    public void updateAnimalList(List<AnimalModel> newAnimalList) {
        animalList.clear();
        animalList.addAll(newAnimalList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_animal, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        ImageView animalImage = holder.itemView.findViewById(R.id.animalImage);
        TextView animalName = holder.itemView.findViewById(R.id.animalName);

        animalName.setText(animalList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    class AnimalViewHolder extends RecyclerView.ViewHolder {

        public AnimalViewHolder(View itemView) {
            super(itemView);
        }
    }
}
