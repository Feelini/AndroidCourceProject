package com.soldatov.mycookbook.repo;

import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Repository {
    CompletableFuture<List<IngredientListEntity>> getAllIngredients();
    CompletableFuture<List<IngredientListUserEntity>> getUserIngredients();
    CompletableFuture<Void> addUserIngredient(String name, String imageUrl);
}
