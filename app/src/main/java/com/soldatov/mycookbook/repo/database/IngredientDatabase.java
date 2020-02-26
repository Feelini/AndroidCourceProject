package com.soldatov.mycookbook.repo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {IngredientListEntity.class, IngredientListUserEntity.class}, version = 2, exportSchema = false)
public abstract class IngredientDatabase extends RoomDatabase {
    private static volatile IngredientDatabase instance;

    public static IngredientDatabase getInstance(final Context context){
        if (instance == null){
            synchronized (IngredientDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            IngredientDatabase.class,
                            "db_ingredient.db")
                            .createFromAsset("db_ingredient.db")
                            .build();
                }
            }
        }

        return instance;
    }

    public abstract IngredientDao ingredientDao();
}
