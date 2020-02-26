package com.soldatov.mycookbook.repo;

import android.app.Application;

import com.soldatov.mycookbook.recipes.Recipe;
import com.soldatov.mycookbook.recipes.RecipeText;
import com.soldatov.mycookbook.repo.api.NetworkService;
import com.soldatov.mycookbook.repo.database.IngredientDao;
import com.soldatov.mycookbook.repo.database.IngredientDatabase;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class RepositoryImpl implements Repository {

    private final ExecutorService EXECUTOR =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final String API_KEY = "e7247b9f2d9041b889517afc46582f30";
    private IngredientDao ingredientDao;

    public RepositoryImpl(Application application) {
        ingredientDao = IngredientDatabase.getInstance(application).ingredientDao();
    }

    @Override
    public CompletableFuture<List<IngredientListEntity>> getAllIngredients() {
        return CompletableFuture.supplyAsync(() -> ingredientDao.getAllIngredients(), EXECUTOR);
    }

    @Override
    public CompletableFuture<List<IngredientListUserEntity>> getUserIngredients() {
        return CompletableFuture.supplyAsync(() -> ingredientDao.getAllUserIngredients(), EXECUTOR);
    }

    @Override
    public CompletableFuture<IngredientListEntity> getUserIngredientById(long id) {
        return CompletableFuture.supplyAsync(() ->
                ingredientDao.getIngredientById(id), EXECUTOR);
    }

    @Override
    public CompletableFuture<Long> addUserIngredient(IngredientListUserEntity ingredientListUserEntity) {
        return CompletableFuture.supplyAsync(() ->
                ingredientDao.insert(ingredientListUserEntity), EXECUTOR);
    }

    @Override
    public CompletableFuture<Void> deleteUserIngredient(long id) {
        return CompletableFuture.supplyAsync(() -> {
            ingredientDao.deleteUserIngredient(id);
            return null;
        }, EXECUTOR);
    }

    @Override
    public CompletableFuture<Call<List<Recipe>>> getRecipesByIngredients(String ingredients) {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getRecipesByIngredients(ingredients, API_KEY));
    }

    @Override
    public CompletableFuture<Call<RecipeText>> getRecipeText(long id) {
        return CompletableFuture.supplyAsync(() ->
                NetworkService.getInstance().getJSONApi().getRecipeText(id, API_KEY));
    }
}
