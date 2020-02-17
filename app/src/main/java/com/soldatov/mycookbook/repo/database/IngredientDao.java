package com.soldatov.mycookbook.repo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IngredientListUserEntity ingredientListUserEntityEntity);

    @Query("SELECT * FROM ingredient_list_entity")
    List<IngredientListEntity> getAllIngredients();

    @Query("SELECT * FROM ingredient_list_user_entity")
    List<IngredientListUserEntity> getAllUserIngredients();

    @Delete
    void deleteEntity(IngredientListUserEntity ingredientListUserEntity);
}
