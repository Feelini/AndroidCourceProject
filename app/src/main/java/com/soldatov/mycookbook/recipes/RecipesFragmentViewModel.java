package com.soldatov.mycookbook.recipes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.soldatov.mycookbook.repo.Repository;

import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesFragmentViewModel extends AndroidViewModel {

    private Repository repository;
    private MutableLiveData<List<Recipe>> liveData = new MutableLiveData<>();
    private MutableLiveData<RecipeText> recipeTextLiveData = new MutableLiveData<>();

    public RecipesFragmentViewModel(@NonNull Application application, Repository repository) {
        super(application);
        this.repository = repository;
    }

    public void fetchRecipes(String ingredients) {
        repository.getRecipesByIngredients(ingredients)
                .thenAccept(listCall -> listCall.enqueue(new Callback<List<Recipe>>() {
                    @Override
                    public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                        liveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Recipe>> call, Throwable t) {

                    }
                }));
    }

    public void fetchRecipeText(long id) {
        repository.getRecipeText(id)
                .thenAccept(recipeTextCall -> recipeTextCall.enqueue(new Callback<RecipeText>() {
                    @Override
                    public void onResponse(Call<RecipeText> call, Response<RecipeText> response) {
                        recipeTextLiveData.postValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<RecipeText> call, Throwable t) {

                    }
                }));
    }

    public LiveData<List<Recipe>> getLiveData() {
        return liveData;
    }
    public LiveData<RecipeText> getRecipeTextLiveData() {
        return recipeTextLiveData;
    }
}
