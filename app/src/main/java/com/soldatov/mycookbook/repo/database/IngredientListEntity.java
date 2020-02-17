package com.soldatov.mycookbook.repo.database;

import androidx.room.Entity;

@Entity(tableName = "ingredient_list_entity")
public class IngredientListEntity extends BaseIngredientList {
    public IngredientListEntity(String name, String imageUrl) {
        super(name, imageUrl);
    }
}
