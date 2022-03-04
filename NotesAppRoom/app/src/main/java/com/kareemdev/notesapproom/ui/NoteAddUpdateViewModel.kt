package com.kareemdev.notesapproom.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import com.kareemdev.notesapproom.database.Note
import com.kareemdev.notesapproom.repository.NoteRepository

class NoteAddUpdateViewModel(application: Application): ViewModel() {
    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note){
        mNoteRepository.insert(note)
    }
    fun update(note:Note){
        mNoteRepository.update(note)
    }
    fun delete(note: Note){
        mNoteRepository.delete(note)
    }
}