package com.trubitsyna.homework.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.domain.AddNoteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteAddViewModel(
    private val addNoteUseCase: AddNoteUseCase = AddNoteUseCase()
): ViewModel() {

    fun onAddClicked(text: String) {
        viewModelScope.launch (Dispatchers.IO) {
                addNoteUseCase.execute(text)
        }
    }

}