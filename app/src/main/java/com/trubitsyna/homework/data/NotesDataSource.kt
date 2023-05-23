package com.trubitsyna.homework.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.trubitsyna.homework.NotesApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

object NotesDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "notes")

    private val applicationContext: Context?
        get() = NotesApplication.getApplicationContext()

    val notesList = mutableListOf<Note>()

    suspend fun addNote(text: String) {
        applicationContext?.let { context ->
            context.dataStore.edit { preferences ->
                val note = Note(text = text)
                val key = stringPreferencesKey(note.id)
                preferences[key] = note.text
            }
        }
        notesList.add(Note(text = text))
    }

    suspend fun deleteNote(id: String) {
        applicationContext?.let { context ->
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey(id))
            }
        }
    }

    fun getNotes(): Flow<List<Note>> {
        return applicationContext?.let { context ->
            context.dataStore.data.map { preferences ->
                preferences.asMap().map { entry ->
                    Note(
                        id = entry.key.name,
                        text = entry.value.toString()
                    )
                }
            }
        } ?: emptyFlow()
    }
}