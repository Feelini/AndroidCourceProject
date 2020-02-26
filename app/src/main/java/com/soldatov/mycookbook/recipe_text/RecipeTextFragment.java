package com.soldatov.mycookbook.recipe_text;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.utils.ViewModelFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeTextFragment extends Fragment {
    @BindView(R.id.recipeText)
    TextView viewRecipeText;

    private Unbinder unbinder;
    private RecipeTextViewModel viewModel;
    private static RecipeTextFragment instance;
    private static long recipeId;

    public static RecipeTextFragment getInstance(long id) {
        recipeId = id;
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

        viewModel.fetchRecipeText(recipeId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

}
