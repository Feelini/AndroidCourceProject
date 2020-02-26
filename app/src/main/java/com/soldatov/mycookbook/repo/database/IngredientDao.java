package com.soldatov.mycookbook.repo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    long insert(IngredientListUserEntity ingredientListUserEntity);

    @Query("INSERT INTO ingredient_list_user_entity (ingredientId) VALUES (:id)")
    long insert(Long id);

    @Query("SELECT * FROM ingredient_list_entity")
    List<IngredientListEntity> getAllIngredients();

    @Query("SELECT * FROM ingredient_list_user_entity")
    List<IngredientListUserEntity> getAllUserIngredients();

    @Query("SELECT u.id, i.name, i.imageUrl " +
            "FROM ingredient_list_user_entity u " +
            "INNER JOIN ingredient_list_entity i ON u.ingredientId = i.id " +
            "WHERE u.ingredientId = :id")
    IngredientListEntity getIngredientById(long id);

    @Query("DELETE FROM ingredient_list_user_entity WHERE id = :id")
    void deleteUserIngredient(long id);

    @Delete
    void deleteUserIngredient(IngredientListUserEntity ingredientListUserEntity);
}
