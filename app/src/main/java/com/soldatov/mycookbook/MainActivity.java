package com.soldatov.mycookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.soldatov.mycookbook.ingredient.IngredientFragment;
import com.soldatov.mycookbook.ingredient_user.IngredientUserFragment;
import com.soldatov.mycookbook.recipe_text.RecipeTextFragment;
import com.soldatov.mycookbook.recipes.Recipe;
import com.soldatov.mycookbook.recipes.RecipeText;
import com.soldatov.mycookbook.recipes.RecipesFragment;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;
import com.soldatov.mycookbook.utils.OnCloseFragmentClickListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IngredientUserFragment.OnAddBtnClickListener, OnCloseFragmentClickListener, IngredientUserFragment.OnSearchRecipesClickListener, RecipesFragment.OnShowRecipeTextClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addIngredientUserFragment();
    }

    private void addIngredientFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new IngredientFragment(), IngredientFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    private void addIngredientUserFragment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, new IngredientUserFragment(), IngredientUserFragment.class.getName())
                .commit();
    }

    private void addRecipesFragment(List<IngredientListUserEntity> ingredientListUserEntities){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, RecipesFragment.getInstance(ingredientListUserEntities),
                        RecipesFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    private void addRecipeTextFragment(Recipe recipe){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, RecipeTextFragment.getInstance(recipe),
                        RecipeTextFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddBtnClick() {
        addIngredientFragment();
    }

    @Override
    public void onCloseFragmentClick() {
        addIngredientUserFragment();
    }

    @Override
    public void onSearchRecipesClick(List<IngredientListUserEntity> ingredients) {
        addRecipesFragment(ingredients);
    }

    @Override
    public void onShowRecipeTextClick(Recipe recipe) {
        addRecipeTextFragment(recipe);
    }
}
