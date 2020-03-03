package com.soldatov.mycookbook.recipe_text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.recipe_text.expandable.RecipeIngredients;
import com.soldatov.mycookbook.recipe_text.expandable.RecipeIngredientsAdapter;
import com.soldatov.mycookbook.recipes.Recipe;
import com.soldatov.mycookbook.utils.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeTextFragment extends Fragment {
    @BindView(R.id.recipeText)
    TextView viewRecipeText;
    @BindView(R.id.ingredientsForRecipe)
    RecyclerView ingredientsForRecipe;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private RecipeTextViewModel viewModel;
    private static RecipeTextFragment instance;
    private static Recipe recipeMy;
    private RecipeIngredientsAdapter adapter;

    public static RecipeTextFragment getInstance(Recipe recipe) {
        recipeMy = recipe;
        if (instance == null) {
            instance = new RecipeTextFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RecipeTextViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getLiveData().observe(
                getViewLifecycleOwner(), recipeText -> {
                    if (recipeText != null) {
                        progressBar.setVisibility(View.INVISIBLE);
                        viewRecipeText.setText(recipeText.getInstructions());
                    }
                }
        );
        return inflater.inflate(R.layout.fragment_recipe_text, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        viewModel.fetchRecipeText(recipeMy.getId());
        progressBar.setVisibility(View.VISIBLE);
        showIngredientsList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void showIngredientsList(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemAnimator animator = ingredientsForRecipe.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        adapter = new RecipeIngredientsAdapter(getRecipeIngredientsList());
        ingredientsForRecipe.setLayoutManager(layoutManager);
        ingredientsForRecipe.setAdapter(adapter);
    }

    private List<RecipeIngredients> getRecipeIngredientsList(){
        List<RecipeIngredients> recipeIngredientsList = new ArrayList<>();

        RecipeIngredients recipeMissedIngredients = new RecipeIngredients("Missed ingredients", recipeMy.getMissedIngredients());
        RecipeIngredients recipeUsedIngredients = new RecipeIngredients("Used ingredients", recipeMy.getUsedIngredients());
        RecipeIngredients recipeUnusedIngredients = new RecipeIngredients("Unused ingredients", recipeMy.getUnusedIngredients());

        recipeIngredientsList.add(recipeMissedIngredients);
        recipeIngredientsList.add(recipeUsedIngredients);
        recipeIngredientsList.add(recipeUnusedIngredients);

        return recipeIngredientsList;
    }

}
