package com.soldatov.mycookbook.repo;

import com.soldatov.mycookbook.recipes.Recipe;
import com.soldatov.mycookbook.recipes.RecipeText;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;

public interface Repository {
    CompletableFuture<List<IngredientListEntity>> getAllIngredients();
    CompletableFuture<List<IngredientListUserEntity>> getUserIngredients();
    CompletableFuture<IngredientListEntity> getUserIngredientById(long id);
    CompletableFuture<Long> addUserIngredient(IngredientListUserEntity ingredientListUserEntity);
    CompletableFuture<Void> deleteUserIngredient(long id);
    CompletableFuture<Call<List<Recipe>>> getRecipesByIngredients(String ingredients);
    CompletableFuture<Call<RecipeText>> getRecipeText(long id);
}
