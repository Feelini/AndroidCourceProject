package com.soldatov.mycookbook.recipes;

import com.google.gson.annotations.SerializedName;

public class RecipeText {
    @SerializedName("instructions")
    private String instructions;

    public RecipeText(String instructions) {
        this.instructions = instructions;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
