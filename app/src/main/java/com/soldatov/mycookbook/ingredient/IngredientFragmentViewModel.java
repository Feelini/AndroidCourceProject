package com.soldatov.mycookbook.ingredient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soldatov.mycookbook.repo.Repository;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;

import java.util.List;
import java.util.concurrent.Executors;

public class IngredientFragmentViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<IngredientListEntity>> liveData = new MutableLiveData<>();

    public IngredientFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchIngredients(){
        repository.getAllIngredients()
                .thenAcceptAsync(ingredientListEntities -> liveData.postValue(ingredientListEntities), Executors.newSingleThreadExecutor());
    }

    public LiveData<List<IngredientListEntity>> getLiveData(){
        return liveData;
    }
}
