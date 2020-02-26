package com.soldatov.mycookbook.recipe_text;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soldatov.mycookbook.recipes.RecipeText;
import com.soldatov.mycookbook.repo.Repository;

import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeTextViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<RecipeText> liveData = new MutableLiveData<>();

    public RecipeTextViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchRecipeText(long id){
        repository.getRecipeText(id).thenAccept(recipeTextCall ->
                recipeTextCall.enqueue(new Callback<RecipeText>() {
            @Override
            public void onResponse(Call<RecipeText> call, Response<RecipeText> response) {
                liveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<RecipeText> call, Throwable t) {

            }
        }));
    }

    public LiveData<RecipeText> getLiveData(){
        return liveData;
    }
}
