package com.kareemdev.notesapproom.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kareemdev.notesapproom.database.Note
import com.kareemdev.notesapproom.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel(){
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes() : LiveData<List<Note>> = mNoteRepository.getAllNotes()
}