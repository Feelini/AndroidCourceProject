package com.soldatov.mycookbook.repo.api;

import com.soldatov.mycookbook.recipes.Recipe;
import com.soldatov.mycookbook.recipes.RecipeText;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("findByIngredients")
    Call<List<Recipe>> getRecipesByIngredients(@Query("ingredients") String ingredients,
                                               @Query("apiKey") String apiKey);

    @GET("{id}/information")
    Call<RecipeText> getRecipeText(@Path("id") long id, @Query("apiKey") String apiKey);
}
