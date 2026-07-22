package com.margin.app.ui.screens.hauls

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.margin.app.domain.repository.HaulRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddHaulUiState(
    val name: String = "",
    val date: Long = System.currentTimeMillis(),
    val notes: String = "",
    val isSaved: Boolean = false
)

@HiltViewModel
class AddHaulViewModel @Inject constructor(
    private val haulRepository: HaulRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddHaulUiState())
    val uiState: StateFlow<AddHaulUiState> = _uiState

    fun onNameChange(value: String) { _uiState.value = _uiState.value.copy(name = value) }
    fun onDateChange(value: Long) { _uiState.value = _uiState.value.copy(date = value) }
    fun onNotesChange(value: String) { _uiState.value = _uiState.value.copy(notes = value) }

    fun save() {
        val state = _uiState.value
        if (state.name.isBlank()) return
        viewModelScope.launch {
            haulRepository.createHaul(
                name = state.name.trim(),
                date = state.date,
                notes = state.notes.trim()
            )
            _uiState.value = _uiState.value.copy(isSaved = true)
        }
    }
}
