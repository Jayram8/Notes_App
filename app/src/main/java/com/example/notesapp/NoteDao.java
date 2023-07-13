package com.example.notesapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.notesapp.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("Select * from note")
    List<Note> getNotes();

    @Insert
    void addNote(Note note);

    @Delete
    void deleteNote(Note note);
}