package com.soldatov.mycookbook.recipe_text.expandable;

import android.view.View;
import android.widget.TextView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends ChildViewHolder {

    @BindView(R.id.ingredient)
    public TextView ingredient;

    public IngredientViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(IngredientListEntity ingredientListEntity){
        String name = ingredientListEntity.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        ingredient.setText(name);
    }
}
