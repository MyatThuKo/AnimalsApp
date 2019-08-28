package com.example.animalsapp;

import android.app.Application;

import com.example.animalsapp.di.PrefsModule;
import com.example.animalsapp.util.SharedPreferencesHelper;

public class PrefsModuleTest extends PrefsModule {

    SharedPreferencesHelper mockPrefs;

    public PrefsModuleTest(SharedPreferencesHelper mockPrefs) {
        this.mockPrefs = mockPrefs;
    }

    @Override
    public SharedPreferencesHelper providesSharedPreferences(Application app) {
        return mockPrefs;
    }
}
