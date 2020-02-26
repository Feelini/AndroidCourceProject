package com.soldatov.mycookbook.recipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.soldatov.mycookbook.R;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;

import java.util.List;

public class RecipesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> groupHeader;
    private List<List<IngredientListEntity>> groups;

    public RecipesExpandableListAdapter(Context context, List<String> groupHeader, List<List<IngredientListEntity>> groups) {
        this.context = context;
        this.groupHeader = groupHeader;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups != null ? groups.size() : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups != null ? groups.get(groupPosition).size() : 0;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupHeader != null ? groupHeader.get(groupPosition) : null;
    }

    @Override
    public IngredientListEntity getChild(int groupPosition, int childPosition) {
        List<IngredientListEntity> ingredient = groups.get(groupPosition);
        return ingredient.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition);
        if (convertView != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.title_group_view, parent, false);
            TextView lblListHeader = convertView.findViewById(R.id.groupTitle);
            lblListHeader.setText(headerTitle);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = getChild(groupPosition, childPosition).getName();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_group_view, parent, false);
        }
        TextView txtListChild = convertView.findViewById(R.id.ingredient);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
