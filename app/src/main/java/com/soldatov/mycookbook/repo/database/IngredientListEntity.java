package com.soldatov.mycookbook.repo.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_list_entity")
public class IngredientListEntity implements Parcelable {

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

    protected IngredientListEntity(Parcel in) {
        id = in.readLong();
        name = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IngredientListEntity> CREATOR = new Creator<IngredientListEntity>() {
        @Override
        public IngredientListEntity createFromParcel(Parcel in) {
            return new IngredientListEntity(in);
        }

        @Override
        public IngredientListEntity[] newArray(int size) {
            return new IngredientListEntity[size];
        }
    };

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
