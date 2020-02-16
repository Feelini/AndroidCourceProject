package com.soldatov.mycookbook.repo.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {IngredientEntity.class}, version = 1, exportSchema = false)
public abstract class IngredientDatabase extends RoomDatabase {
    private static volatile IngredientDatabase instance;

    public static IngredientDatabase getInstance(final Application application){
        if (instance == null){
            synchronized (IngredientDatabase.class){
                if (instance == null){
                    instance = Room.databaseBuilder(application.getApplicationContext(),
                            IngredientDatabase.class,
                            "db_ingredient.db")
                            .build();
                }
            }
        }

        return instance;
    }
}
