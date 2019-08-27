package com.example.animalsapp.viewmodel;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.animalsapp.model.AnimalApiService;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.model.ApiKeyModel;
import com.example.animalsapp.util.SharedPreferencesHelper;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ListViewModel extends AndroidViewModel {

    private AnimalApiService apiService = new AnimalApiService();

    private CompositeDisposable disposable = new CompositeDisposable();

    public MutableLiveData<List<AnimalModel>> animals = new MutableLiveData<List<AnimalModel>>();
    public MutableLiveData<Boolean> error = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();

    private SharedPreferencesHelper prefs;

    private Boolean invalidApiKey = false;

    public ListViewModel(Application application) {
        super(application);
        prefs = new SharedPreferencesHelper(application);
    }

    public void refresh() {
        loading.setValue(true);
        invalidApiKey = false;
        String key = prefs.getApiKey();
        if (key == null || key.equals("")) {
            getKey();
        } else {
            getAnimals(key);
        }
    }

    public void hardRefresh() {
        loading.setValue(true);
        getKey();
    }

    private void getKey() {
        disposable.add(
                apiService.getAPIKey()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<ApiKeyModel>() {
                            @Override
                            public void onSuccess(ApiKeyModel keyModel) {
                                if (keyModel.key.isEmpty()) {
                                    error.setValue(true);
                                    loading.setValue(false);
                                } else {
                                    prefs.saveApiKey(keyModel.key);
                                    getAnimals(keyModel.key);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!invalidApiKey) {
                                    invalidApiKey = true;
                                    getKey();
                                } else {
                                    e.printStackTrace();
                                    loading.setValue(false);
                                    error.setValue(true);
                                }
                            }
                        })
        );
    }

    private void getAnimals(String key) {
        disposable.add(
                apiService.getAnimals(key)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<List<AnimalModel>>() {
                            @Override
                            public void onSuccess(List<AnimalModel> list) {
                                animals.setValue(list);
                                loading.setValue(false);
                                error.setValue(false);
                            }

                            @Override
                            public void onError(Throwable e) {
                                loading.setValue(false);
                                error.setValue(true);
                                e.printStackTrace();
                            }
                        })
        );

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
