package com.soldatov.mycookbook.repo.database;

import androidx.room.Entity;

@Entity(tableName = "ingredient_list_user_entity")
public class IngredientListUserEntity extends BaseIngredientList{
    public IngredientListUserEntity(String name, String imageUrl) {
        super(name, imageUrl);
    }
}