package com.soldatov.mycookbook.repo.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_list_entity")
public class IngredientListEntity {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String imageUrl;

    @Ignore
    public IngredientListEntity(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public IngredientListEntity(Long id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
}
