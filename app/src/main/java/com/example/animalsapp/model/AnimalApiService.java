package com.example.animalsapp.model;

import com.example.animalsapp.di.DaggerApiComponent;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class AnimalApiService {

    @Inject
    AnimalAPI api;

    public AnimalApiService() {
        DaggerApiComponent.create().inject(this);
    }

    public Single<ApiKeyModel> getAPIKey() {
        return api.getAPIKey();
    }

    public Single<List<AnimalModel>> getAnimals(String key) {
        return api.getAnimals(key);
    }
}
