package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.notesapp.database.NoteDataBase
import com.example.notesapp.repository.NoteRepository
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()

    }
    private fun setUpViewModel(){
        val noteRepository=NoteRepository(NoteDataBase(this))
        val viewModelFactory=ViewModelFactory(app = application,noteRepository)
        noteViewModel=ViewModelProvider(this,viewModelFactory)[NoteViewModel::class.java]
    }
}