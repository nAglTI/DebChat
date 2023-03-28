package com.nagl.debchat.ui.dialogs

import androidx.lifecycle.*
import com.nagl.debchat.data.model.Dialog
import com.nagl.debchat.data.model.net.NetDialog
import com.nagl.debchat.data.model.UserToken
import com.nagl.debchat.data.source.repository.DataStoreRepository
import com.nagl.debchat.data.source.repository.IDataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DialogsViewModel @Inject constructor(
    private val dataRepository: IDataRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userToken = MutableLiveData<UserToken?>()
    val userToken: LiveData<UserToken?> = _userToken

    private val _dialogs = MutableLiveData<List<Dialog>?>()
    val dialogs: LiveData<List<Dialog>?> = _dialogs

    fun getUserToken() {
        _isLoading.value = true
        viewModelScope.launch {
            dataStoreRepository.token.collect {
                _userToken.postValue(UserToken(it))
            }
        }
    }

    fun getUserDialogs(userToken: String) {
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dataRepository.getUserDialogs(userToken)) {
                is com.nagl.debchat.utils.Result.Success -> {
                    _isLoading.value = false
                    if (result.data != null) {
                        _dialogs.value = result.data
                    } else {
                        _dialogs.value = arrayListOf()
                    }
                }
                is com.nagl.debchat.utils.Result.Error -> { // todo: error codes handler later
                    _isLoading.value = false
                    _dialogs.value = arrayListOf()
                }
                is com.nagl.debchat.utils.Result.Loading ->
                    _isLoading.postValue(true)
            }
        }
    }
}