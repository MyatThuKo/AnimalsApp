package com.example.animalsapp;

import com.example.animalsapp.di.ApiModule;
import com.example.animalsapp.model.AnimalApiService;

public class ApiModuleTest extends ApiModule {

    AnimalApiService mockService;

    public ApiModuleTest(AnimalApiService mockService) {
        this.mockService = mockService;
    }

    @Override
    public AnimalApiService provideAnimalAPIService() {
        return mockService;
    }
}
