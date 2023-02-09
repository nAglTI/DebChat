package com.nagl.debchat.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagl.debchat.data.source.repository.DataStoreRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Settings"
    }
    val text: LiveData<String> = _text

    private val _isLogOut = MutableLiveData<Boolean>()
    val isLogOut: LiveData<Boolean> = _isLogOut

    fun logOut() {
        viewModelScope.launch {
            dataStoreRepository.clearData()
            _isLogOut.value = true
        }
    }
}