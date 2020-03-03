package com.soldatov.mycookbook.recipes;

import com.google.gson.annotations.SerializedName;
import com.soldatov.mycookbook.repo.database.IngredientListEntity;

import java.util.List;

public class Recipe {

    private long id;
    @SerializedName("title")
    private String name;
    @SerializedName("image")
    private String imageUrl;
    private List<IngredientListEntity> missedIngredients;
    private List<IngredientListEntity> usedIngredients;
    private List<IngredientListEntity> unusedIngredients;
    private String usedIngredientCount;
    private String missedIngredientCount;

    public Recipe(long id, String name, String imageUrl, List<IngredientListEntity> missedIngredients, List<IngredientListEntity> usedIngredients, List<IngredientListEntity> unusedIngredients, String usedIngredientCount, String missedIngredientCount) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.missedIngredients = missedIngredients;
        this.usedIngredients = usedIngredients;
        this.unusedIngredients = unusedIngredients;
        this.usedIngredientCount = usedIngredientCount;
        this.missedIngredientCount = missedIngredientCount;
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

    public List<IngredientListEntity> getMissedIngredients() {
        return missedIngredients;
    }

    public void setMissedIngredients(List<IngredientListEntity> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public List<IngredientListEntity> getUsedIngredients() {
        return usedIngredients;
    }

    public void setUsedIngredients(List<IngredientListEntity> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

    public List<IngredientListEntity> getUnusedIngredients() {
        return unusedIngredients;
    }

    public void setUnusedIngredients(List<IngredientListEntity> unusedIngredients) {
        this.unusedIngredients = unusedIngredients;
    }

    public String getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(String usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public String getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(String missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }
}
