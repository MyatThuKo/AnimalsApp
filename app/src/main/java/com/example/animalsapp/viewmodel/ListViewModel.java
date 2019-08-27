package com.example.animalsapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.animalsapp.model.AnimalModel;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<List<AnimalModel>>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    public void refresh() {
        AnimalModel animal1 = new AnimalModel("Dog");
        AnimalModel animal2 = new AnimalModel("Lion");
        AnimalModel animal3 = new AnimalModel("Rabbit");
        AnimalModel animal4 = new AnimalModel("Dog");
        AnimalModel animal5 = new AnimalModel("Lion");
        AnimalModel animal6 = new AnimalModel("Rabbit");
        AnimalModel animal7 = new AnimalModel("Dog");
        AnimalModel animal8 = new AnimalModel("Lion");
        AnimalModel animal9 = new AnimalModel("Rabbit");

        List<AnimalModel> list = new ArrayList<>();
        list.add(animal1);
        list.add(animal2);
        list.add(animal3);
        list.add(animal4);
        list.add(animal5);
        list.add(animal6);
        list.add(animal7);
        list.add(animal8);
        list.add(animal9);

        animals.setValue(list);
        error.setValue(false);
        loading.setValue(false);
    }
}
