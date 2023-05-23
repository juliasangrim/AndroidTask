package com.trubitsyna.homework.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.domain.DeleteImageUseCase
import com.trubitsyna.homework.domain.DeleteNoteUseCase
import com.trubitsyna.homework.domain.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
) : ViewModel() {

    private val _notesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData: LiveData<List<Note>> = _notesListLiveData

    fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase.execute().collect { list ->
                _notesListLiveData.value = list.map {
                    it.copy(
                        id = it.id,
                        text = it.text,
                        imageUri = it.imageUri
                    )
                }
            }
        }
    }

    fun onDeleteClicked(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(note)
            note.imageUri?.let { deleteImageUseCase.execute(it) }

            getNotes()
        }
    }

}