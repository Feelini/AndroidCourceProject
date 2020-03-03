package com.soldatov.mycookbook.ingredient_user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soldatov.mycookbook.repo.Repository;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;

public class IngredientUserFragmentViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<IngredientListUserEntity>> liveData = new MutableLiveData<>();
    private MutableLiveData<Long> newIngredientId = new MutableLiveData<>();

    public IngredientUserFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchIngredients() {
        repository.getUserIngredients()
                .thenAccept(ingredientListUserEntities -> liveData.postValue(ingredientListUserEntities));
    }

    public void deleteIngredient(long ingredientId) {
        repository.deleteUserIngredient(ingredientId);
    }

    public void addUserIngredient(IngredientListUserEntity ingredientListUserEntity) {
        repository.addUserIngredient(ingredientListUserEntity)
                .thenAccept(aLong -> newIngredientId.postValue(aLong));
    }

    public LiveData<List<IngredientListUserEntity>> getLiveData() {
        return liveData;
    }

    public LiveData<Long> getNewIngredientId(){
        return newIngredientId;
    }
}
