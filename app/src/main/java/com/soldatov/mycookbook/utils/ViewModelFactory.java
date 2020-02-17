package com.soldatov.mycookbook.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.soldatov.mycookbook.ingredient.IngredientFragmentViewModel;
import com.soldatov.mycookbook.ingredient_user.IngredientUserFragmentViewModel;
import com.soldatov.mycookbook.repo.RepositoryImpl;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public ViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(IngredientUserFragmentViewModel.class)) {
            return (T) new IngredientUserFragmentViewModel(application, new RepositoryImpl(application));
        }
        if (modelClass.isAssignableFrom(IngredientFragmentViewModel.class)) {
            return (T) new IngredientFragmentViewModel(application, new RepositoryImpl(application));
        }
        return null;
    }
}
