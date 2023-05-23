package com.trubitsyna.homework.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.domain.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel() {

    private val _searchNoteListLiveData = MutableLiveData<List<Note>>()
    val searchNoteListLiveData: LiveData<List<Note>> = _searchNoteListLiveData
    fun onSearchClicked(searchRequest: String) {
        viewModelScope.launch {
            getNotesUseCase.execute().collect { list ->
                _searchNoteListLiveData.value = list.filter {
                    it.text.contains(searchRequest, ignoreCase = true)
                }
            }
        }
    }

    fun getAllNotes() {
        return onSearchClicked("")
    }
}