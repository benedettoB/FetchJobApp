package com.benedetto.awsfetchjobs.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benedetto.domain.models.Item
import com.benedetto.domain.usecases.GetItemsUseCase
import com.benedetto.domain.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val networkUtils: NetworkUtils
) : ViewModel() {

    private val TAG = "ItemViewModel"

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _itemsState: MutableStateFlow<Map<Int, List<Item>>> = MutableStateFlow(emptyMap())
    val itemsState: StateFlow<Map<Int, List<Item>>> = _itemsState

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            getItemsUseCase.invoke()
                .catch { exception ->
                    if (networkUtils.isConnected()) {
                        _uiState.value = UiState.GenericError
                        Log.e(TAG, exception.message, exception)
                    } else {
                        _uiState.value = UiState.NetworkError
                        Log.e(TAG, exception.message, exception)
                    }

                }
                .buffer()
                .collect { itemMap ->
                    _itemsState.value += itemMap
                    _uiState.value = UiState.Success
                }

        }
    }
}

sealed class UiState {
    data object Success : UiState()
    data object GenericError : UiState()
    data object NetworkError : UiState()
    data object Loading : UiState()
}

