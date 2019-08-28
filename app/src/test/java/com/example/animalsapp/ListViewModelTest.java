package com.example.animalsapp;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.animalsapp.di.AppModule;
import com.example.animalsapp.di.DaggerViewModelComponent;
import com.example.animalsapp.model.AnimalApiService;
import com.example.animalsapp.model.AnimalModel;
import com.example.animalsapp.model.ApiKeyModel;
import com.example.animalsapp.util.SharedPreferencesHelper;
import com.example.animalsapp.viewmodel.ListViewModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class ListViewModelTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    AnimalApiService animalService;

    @Mock
    SharedPreferencesHelper prefs;

    Application application = Mockito.mock(Application.class);

    ListViewModel listViewModel = new ListViewModel(application, true);

    private String key = "Test Key";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        DaggerViewModelComponent.builder()
                .appModule(new AppModule(application))
                .apiModule(new ApiModuleTest(animalService))
                .prefsModule(new PrefsModuleTest(prefs))
                .build()
                .inject(listViewModel);
    }

    @Test
    public void getAnimalsSuccess() {
        Mockito.when(prefs.getApiKey()).thenReturn(key);
        AnimalModel animal = new AnimalModel("cow");
        ArrayList<AnimalModel> animalList = new ArrayList<>();
        animalList.add(animal);

        Single testSingle = Single.just(animalList);

        Mockito.when(animalService.getAnimals(key)).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1, listViewModel.animals.getValue().size());
        Assert.assertEquals(false, listViewModel.error.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

    @Test
    public void getAnimalsFail() {
        Mockito.when(prefs.getApiKey()).thenReturn(key);
        Single testSingle = Single.error(new Throwable());
        Single keySingle = Single.just(new ApiKeyModel("OK", key));

        Mockito.when(animalService.getAnimals(key)).thenReturn(testSingle);
        Mockito.when(animalService.getAPIKey()).thenReturn(keySingle);

        listViewModel.refresh();

        Assert.assertEquals(null, listViewModel.animals.getValue());
        Assert.assertEquals(true, listViewModel.error.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

    @Test
    public void getKeySuccess() {
        Mockito.when(prefs.getApiKey()).thenReturn(null);
        ApiKeyModel apiKey = new ApiKeyModel("OK", key);
        Single keySingle = Single.just(apiKey);

        Mockito.when(animalService.getAPIKey()).thenReturn(keySingle);

        AnimalModel animal = new AnimalModel("cow");
        ArrayList<AnimalModel> animalList = new ArrayList<>();
        animalList.add(animal);

        Single testSingle = Single.just(animalList);

        Mockito.when(animalService.getAnimals(key)).thenReturn(testSingle);

        listViewModel.refresh();

        Assert.assertEquals(1, listViewModel.animals.getValue().size());
        Assert.assertEquals(false, listViewModel.error.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

    @Test
    public void getKeyFail() {
        Mockito.when(prefs.getApiKey()).thenReturn(null);
        Single keySingle = Single.error(new Throwable());

        Mockito.when(animalService.getAPIKey()).thenReturn(keySingle);

        listViewModel.refresh();

        Assert.assertEquals(null, listViewModel.animals.getValue());
        Assert.assertEquals(true, listViewModel.error.getValue());
        Assert.assertEquals(false, listViewModel.loading.getValue());
    }

    @Before
    public void setUpRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(runnable -> {
                    runnable.run();
                }, true);
            }
        };

        RxJavaPlugins.setInitNewThreadSchedulerHandler(schedulerCallable -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> immediate);
    }
}
