package com.soldatov.mycookbook.recipe_text.expandable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soldatov.mycookbook.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RecipeIngredientsAdapter extends ExpandableRecyclerViewAdapter<RecipeViewHolder, IngredientViewHolder> {

    private Context context;

    public RecipeIngredientsAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public RecipeViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_group_view, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public IngredientViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_view, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(IngredientViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        holder.bindView(((RecipeIngredients) group).getItems().get(childIndex));
    }

    @Override
    public void onBindGroupViewHolder(RecipeViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setGroupTitle(group.getTitle(), group.getItemCount(), context);
    }
}
