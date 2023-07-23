package com.example.thecoffeehouse.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "profile")
public class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "field")
    private String field;

    @ColumnInfo(name = "edited_text")
    private String editedText;

    public ProfileEntity(String field, String editedText) {
        this.field = field;
        this.editedText = editedText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getEditedText() {
        return editedText;
    }

    public void setEditedText(String editedText) {
        this.editedText = editedText;
    }
}

