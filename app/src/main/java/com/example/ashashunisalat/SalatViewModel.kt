package com.example.ashashunisalat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SalatItem(val name: String, val time: String)
data class UiState(
    val isLoading: Boolean = false,
    val times: List<SalatItem> = emptyList(),
    val error: String? = null
)

class SalatViewModel : ViewModel() {
    private val repo = SalatRepository()
    private val _state = MutableStateFlow(UiState(isLoading = true))
    val state: StateFlow<UiState> = _state

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            try {
                _state.value = UiState(isLoading = true)
                val res = repo.fetchTimes()
                val list = res.data.map { (key, value) ->
                    SalatItem(key, value.short)
                }
                _state.value = UiState(times = list)
            } catch (e: Exception) {
                _state.value = UiState(error = e.localizedMessage)
            }
        }
    }
}