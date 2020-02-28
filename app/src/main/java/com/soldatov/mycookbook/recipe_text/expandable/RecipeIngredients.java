package com.soldatov.mycookbook.recipe_text.expandable;

import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RecipeIngredients extends ExpandableGroup<IngredientListEntity> {
    public RecipeIngredients(String title, List<IngredientListEntity> items) {
        super(title, items);
    }
}
