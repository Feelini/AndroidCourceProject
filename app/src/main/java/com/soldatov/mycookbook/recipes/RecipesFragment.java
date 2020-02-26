package com.soldatov.mycookbook.recipes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.soldatov.mycookbook.repo.database.IngredientListUserEntity;
import com.soldatov.mycookbook.utils.OnCloseFragmentClickListener;
import com.soldatov.mycookbook.utils.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipesFragment extends Fragment {

    @BindView(R.id.recipeToolbar)
    Toolbar recipeToolbar;
    @BindView(R.id.viewRecipesList)
    RecyclerView viewRecipesList;

    private RecipesFragmentViewModel viewModel;
    private Unbinder unbinder;
    private OnCloseFragmentClickListener onCloseFragmentClickListener;
    private static RecipesFragment instance;
    private static List<IngredientListUserEntity> ingredients;
    private RecipesAdapter adapter;
    private OnShowRecipeTextClickListener onShowRecipeTextClickListener;
    private List<String> groupHeader = new ArrayList<>();

    public static RecipesFragment getInstance(List<IngredientListUserEntity> ingredientUserList) {
        ingredients = ingredientUserList;
        if (instance == null) {
            instance = new RecipesFragment();
        }
        return instance;
    }

    public interface OnShowRecipeTextClickListener{
        void onShowRecipeTextClick(long id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnCloseFragmentClickListener) {
            onCloseFragmentClickListener = (OnCloseFragmentClickListener) context;
        }
        if (context instanceof OnShowRecipeTextClickListener){
            onShowRecipeTextClickListener = (OnShowRecipeTextClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (onCloseFragmentClickListener != null) {
            onCloseFragmentClickListener = null;
        }
        if (onShowRecipeTextClickListener != null){
            onShowRecipeTextClickListener = null;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewModelFactory viewModelFactory = new ViewModelFactory(getActivity().getApplication());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RecipesFragmentViewModel.class);
        groupHeader.add("Missed Ingredients");
        groupHeader.add("Used Ingredients");
        groupHeader.add("Unused Ingredients");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel.getLiveData().observe(
                getViewLifecycleOwner(),
                recipes -> showRecipesList(recipes)
        );
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        recipeToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        recipeToolbar.setNavigationOnClickListener(v -> onCloseFragmentClickListener.onCloseFragmentClick());

        viewModel.fetchRecipes(createIngredientsString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private String createIngredientsString() {
        StringBuilder ingredientsStringBuilder = new StringBuilder();
        for (IngredientListUserEntity ingredient : ingredients) {
            ingredientsStringBuilder.append(ingredient.getName())
                    .append(",+");
        }
        ingredientsStringBuilder.setLength(ingredientsStringBuilder.length() - 2);
        String ingredientsString = Pattern.compile(" ").matcher(ingredientsStringBuilder).replaceAll("%20");
        return ingredientsString;
    }

    private void showRecipesList(List<Recipe> recipes) {
        adapter = new RecipesAdapter(recipes);
        viewRecipesList.setAdapter(adapter);
        viewRecipesList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }

    public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

        private List<Recipe> recipesList;

        public RecipesAdapter(List<Recipe> recipesList) {
            this.recipesList = recipesList;
        }

        @NonNull
        @Override
        public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resipe_list, parent, false);
            return new RecipesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {
            holder.bindData(recipesList.get(position));
        }

        @Override
        public int getItemCount() {
            return recipesList != null ? recipesList.size() : 0;
        }

        public class RecipesViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.recipeImage)
            ImageView recipeImage;
            @BindView(R.id.recipeName)
            TextView recipeName;
            @BindView(R.id.ingredients)
            ExpandableListView ingredients;

            public RecipesViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(Recipe recipe) {
                List<List<IngredientListEntity>> ingredientsList = new ArrayList<>();
                ingredientsList.add(recipe.getMissedIngredients());
                ingredientsList.add(recipe.getUsedIngredients());
                ingredientsList.add(recipe.getUnusedIngredients());
                Picasso.get().load(recipe.getImageUrl()).into(recipeImage);
                recipeName.setText(recipe.getName());
                ingredients.setAdapter(new RecipesExpandableListAdapter(getContext(), groupHeader, ingredientsList));
                itemView.setOnClickListener(v -> onShowRecipeTextClickListener.onShowRecipeTextClick(recipe.getId()));
            }
        }
    }
}
