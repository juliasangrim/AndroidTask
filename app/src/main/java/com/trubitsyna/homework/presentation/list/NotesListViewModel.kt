package com.trubitsyna.homework.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.data.Note
import com.trubitsyna.homework.domain.DeleteNoteUseCase
import com.trubitsyna.homework.domain.GetNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesListViewModel(
    private val getNotesUseCase: GetNotesUseCase = GetNotesUseCase(),
    private val deleteNoteUseCase: DeleteNoteUseCase = DeleteNoteUseCase()
) : ViewModel() {

    private val _notesListLiveData = MutableLiveData<List<Note>>()
    val notesListLiveData: LiveData<List<Note>> = _notesListLiveData


    fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase.execute().collect { list ->
                _notesListLiveData.value = list.map {
                    it.copy(
                        text = it.text
                    )
                }
            }
        }
    }

    fun onDeleteClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(id)
            getNotes()
        }
    }

}