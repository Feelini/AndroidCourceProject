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
    void insert(IngredientEntity ingredientEntity);

    @Query("SELECT * FROM ingredient_entity")
    List<IngredientEntity> getAll();

    @Query("SELECT * FROM ingredient_entity WHERE id = :id")
    IngredientEntity getEntity(long id);

    @Delete
    void deleteEntity(IngredientEntity ingredientEntity);
}
