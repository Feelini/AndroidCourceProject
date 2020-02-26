package com.soldatov.mycookbook.repo.database;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_list_user_entity", indices = {@Index(value = "ingredientId", unique = true)})
public class IngredientListUserEntity{

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long ingredientId;
    private String name;
    private String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public IngredientListUserEntity(long ingredientId, String name, String imageUrl) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
}