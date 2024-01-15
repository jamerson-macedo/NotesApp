package com.example.notesapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.MainActivity
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewModel

class EditFragment : Fragment(R.layout.fragment_edit), MenuProvider {
    private var editBind: FragmentEditBinding? = null
    private val binding get() = editBind!!
    private lateinit var viewModel: NoteViewModel
    private lateinit var currentNote: Note
    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        editBind = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        viewModel = (activity as MainActivity).noteViewModel
        // pegando os dados que foram passados
        currentNote = args.note!!

        binding.editNoteTitle.setText(currentNote.title)
        binding.editNoteDesc.setText(currentNote.description)
        binding.editNoteFab.setOnClickListener {
            val notetitle = binding.editNoteTitle.text.toString().trim()
            val notedesc = binding.editNoteDesc.text.toString().trim()
            if (notetitle.isNotEmpty()) {

                val note = Note(currentNote.id, notetitle, notedesc)
                viewModel.updateNote(note)
                view.findNavController()
                    .popBackStack(R.id.homeFragment, false)
            } else {
                Toast.makeText(context, "Inset a Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("delete Note?")
            setMessage("Do you Want Delete this Note?")
            setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(currentNote)
                Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment, false)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit_note, menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.deleteMenu -> {
                deleteNote()
                true
            }

            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editBind=null
    }


}