package com.soldatov.mycookbook.repo;

import android.app.Application;

import com.soldatov.mycookbook.repo.database.IngredientDao;
import com.soldatov.mycookbook.repo.database.IngredientDatabase;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepositoryImpl implements Repository {

    private final ExecutorService EXECUTOR =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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
    public CompletableFuture<Void> addUserIngredient(String name, String imageUrl) {
        IngredientListUserEntity newIngredient = new IngredientListUserEntity(name, imageUrl);
        return CompletableFuture.supplyAsync(
                () -> {
                    ingredientDao.insert(newIngredient);
                    return null;
                },
                EXECUTOR);
    }
}
