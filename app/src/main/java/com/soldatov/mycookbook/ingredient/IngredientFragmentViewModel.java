package com.soldatov.mycookbook.ingredient;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soldatov.mycookbook.repo.Repository;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;

public class IngredientFragmentViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<IngredientListEntity>> liveData = new MutableLiveData<>();

    public IngredientFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchIngredients(){
        repository.getAllIngredients()
                .thenAccept(ingredientListEntities -> liveData.postValue(ingredientListEntities));
    }

    public void addUserIngredients(List<IngredientListUserEntity> checkedIngredients){
        for (IngredientListUserEntity ingredient: checkedIngredients){
            repository.addUserIngredient(ingredient);
        }
    }

    public LiveData<List<IngredientListEntity>> getLiveData(){
        return liveData;
    }
}
