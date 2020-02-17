package com.soldatov.mycookbook.repo.database;

import androidx.room.PrimaryKey;

public class BaseIngredientList {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String imageUrl;

    public BaseIngredientList(String name, String imageUrl) {
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
