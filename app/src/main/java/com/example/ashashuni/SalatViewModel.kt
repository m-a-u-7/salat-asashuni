package com.ashashuni.salat.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ashashuni.salat.data.ApiResponse
import com.ashashuni.salat.data.SalatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SalatViewModel(app: Application): AndroidViewModel(app) {
    private val repo = SalatRepository(app.applicationContext)

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    init { refresh() }

    fun refresh() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            val result = repo.fetch(lat = "22.5500", lon = "89.1681")
            result.onSuccess { _state.value = UiState.Ready(it) }
                .onFailure { _state.value = UiState.Error(it.message ?: "ডেটা পাওয়া যায়নি") }
        }
    }

    sealed interface UiState {
        data object Loading : UiState
        data class Ready(val data: ApiResponse) : UiState
        data class Error(val msg: String) : UiState
    }
}
