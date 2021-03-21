package com.example.flashcardapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Flashcard.class}, version = 3)
public abstract class AppDatabase {
    public abstract FlashcardDao flashcardDao();
}
